package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemsController {
    private final ProductService productService;

    public ItemsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getMethodName(Model model, @PathVariable long id) {
        Product myPro = this.productService.fetchProductById(id).get();
        model.addAttribute("detailPro", myPro);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        // Vì getAttribute là 1 object nên phải ép kiểu sang String
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, productId, session, 1);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        // join 2 bảng lại với nhau để lấy ra cartDetail của người dùng đó
        Cart cart = this.productService.fetchByUser(currentUser);

        // Nếu người dùng có giỏ hàng rỗng thì cartDetail gán bằng mảng rỗng và ngược
        // lại
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetail();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartProduct(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleDeleteProductInCart(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        // lấy được giỏ hàng của user chứa id đó bằng cách join 2 bảng lại với nhau
        Cart cart = this.productService.fetchByUser(currentUser);
        List<CartDetail> cartDetail = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetail();
        double totalPrice = 0;
        for (CartDetail cd : cartDetail) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetail);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetail();
        this.productService.handleUpdateCartBeforeCheckOut(cartDetails);
        return "redirect:/checkout";
    }

    @GetMapping("/thanks")
    public String getThankYouPage() {
        return "client/cart/thanks";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest request, @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {

        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(
            @RequestParam("id") long id,
            @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/products")
    public String getShopPage(Model model, @RequestParam("page") Optional<String> pageOptional,
            @RequestParam("name") Optional<String> nameOptional,
            @RequestParam("min-price") Optional<Double> priceOptional,
            @RequestParam("max-price") Optional<Double> priceOptional2,
            @RequestParam("factory") Optional<String> factoryNameOptional,
            @RequestParam("price") Optional<String> price) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 6);

        // Câu điều kiện nếu client truyền vào name thì lấy data bằng .get() ngược
        // lại gán bằng chuỗi rỗng

        
        // String name = nameOptional.isPresent() ? nameOptional.get() : " ";
        // Page<Product> products = this.productService.getAllProductWithSpec( pageable,
        // name);

        // case 1
        // double minPrice = priceOptional.isPresent() ? priceOptional.get() : 0;
        // Page<Product> products =
        // this.productService.getAllProductWithMinPrice(pageable, minPrice);

        // case 2
        // double maxPrice = priceOptional2.isPresent() ? priceOptional2.get() : Double.MAX_VALUE;
        // Page<Product> products = this.productService.getAllProductWithMaxPrice(pageable, maxPrice);

        // case 3
        // String factoryName = factoryNameOptional.isPresent() ? factoryNameOptional.get() : null;
        // Page<Product> products = this.productService.getAllProductWithFactory(pageable, factoryName);

        // case 4
        // asList chuyển mảng thành danh sách
        // List<String> factory = Arrays.asList(factoryNameOptional.get().split(","));
        // Page<Product> products = this.productService.getAllProductWithListFactory(pageable, factory);

        // case 5
        // String rangePrice = price.isPresent() ? price.get() : " ";
        // Page<Product> products = this.productService.getProductInRangePrice(pageable, rangePrice);
        
        //case 6
        List<String> rangePrice = Arrays.asList(price.get().split(","));
        Page<Product> products = this.productService.fetchProductWithSpec(pageable, rangePrice);

        List<Product> listProduct = products.getContent();
        model.addAttribute("products", listProduct);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "client/product/show";
    }

}
