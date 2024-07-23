package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;

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
        String email =(String) session.getAttribute("email");
        this.productService.hanldeAddProductToCart(email, productId, session); 
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model,  HttpServletRequest request){
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        // join 2 bảng lại với nhau để lấy ra cartDetail của người dùng đó 
        Cart cart = this.productService.fetchByUser(currentUser);

        // Nếu người dùng có giỏ hàng rỗng thì cartDetail gán bằng mảng rỗng và ngược lại
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCart_details();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails){
            totalPrice += cd.getPrice() * cd.getQuantity(); 
        }        
        model.addAttribute("cartDetails1", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartProduct(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;  
        this.productService.handleDeleteProductInCart(cartDetailId, session);       
        return "redirect:/cart";
    }
    


}
