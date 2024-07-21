package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public List<Product> getAllProduct() {
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

    public void hanldeAddProductToCart(String email, long productId) {
        User user = this.userService.getUserByEmail(email);
        if(user !=  null) {
            // Check user đã có cart chưa 
            Cart cart = this.cartRepository.findByUser(user);

            // Nếu chưa
            if(cart == null){
                // Tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUsers(user);
                otherCart.setSum(1);

                // lưu cart 
                cart = this.cartRepository.save(otherCart);
            }

            // save CartDetail
            CartDetail cartDetail = new CartDetail();
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if(productOptional.isPresent()){
                Product reaProduct = productOptional.get();
                cartDetail.setCarts(cart);
                cartDetail.setProducts(reaProduct);
                cartDetail.setPrice(reaProduct.getPrice());
                cartDetail.setQuantity(1);
                this.cartDetailRepository.save(cartDetail);
            }
        }
    }
}
