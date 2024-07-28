package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Page<Product> getAllProduct(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public List<Product> fetchProduct(){
        return this.productRepository.findAll();
    }

    public Product handleSaveProduct(Product laptop) {
        return this.productRepository.save(laptop);
    }

    public Optional<Product> getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public Product getImage(String image) {
        return this.productRepository.findByImage(image);
    }

    public void handleDeleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // Check user đã có cart chưa
            Cart cart = this.cartRepository.findByUser(user);

            // Nếu chưa
            if (cart == null) {
                // Tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUsers(user);
                otherCart.setSum(0);

                // lưu cart
                cart = this.cartRepository.save(otherCart);
            }

            // save CartDetail
            CartDetail cartDetail = new CartDetail();
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();
                // Kiểm tra xem sản phẩm đó trước đó đã có trong giỏ hàng hay chưa
                CartDetail oldCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                // Nếu sản phẩm đó chưa có trong giỏ hàng
                if (oldCartDetail == null) {
                    cartDetail.setCarts(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(quantity);
                    this.cartDetailRepository.save(cartDetail);

                    // update Cart (sum)
                    long s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldCartDetail);
                }
            }
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleDeleteProductInCart(long id, HttpSession session) {
        Optional<CartDetail> cartDetail = this.cartDetailRepository.findById(id);
        if (cartDetail.isPresent()) {
            Cart currentCart = cartDetail.get().getCarts();
            this.cartDetailRepository.deleteById(id);

            long s = currentCart.getSum();
            if (s > 1) {
                s -= 1;
                currentCart.setSum(s);
                this.cartRepository.save(currentCart);
                session.setAttribute("sum", s);
            } else {
                this.cartRepository.delete(currentCart);
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckOut(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cartDetailOptional.isPresent()) {
                CartDetail currentCartDetail = cartDetailOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        // Step1 : get Cart by User
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetail();
            
            // Tạo orderDetail
            
            if (cartDetails != null) {
                // Tạo order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double totalPrice = 0;
                for(CartDetail cd : cartDetails){
                    totalPrice += cd.getPrice() * cd.getQuantity();
                }

                order.setTotalPrice(totalPrice);
                order = this.orderRepository.save(order);

                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    orderDetail.setPrice(cartDetail.getPrice());
                    this.orderDetailRepository.save(orderDetail);
                }

                // step 2: Xóa cart_detail và cart
                for (CartDetail cartDetail : cartDetails) {
                    this.cartDetailRepository.deleteById(cartDetail.getId());
                }
                this.cartRepository.deleteById(cart.getId());
            }

            // step 3: update session
            session.setAttribute("sum", 0);
        }
    }
}
