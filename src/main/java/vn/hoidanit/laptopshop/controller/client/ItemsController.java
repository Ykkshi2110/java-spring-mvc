package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
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
    public String postMethodName(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        // Vì getAttribute là 1 object nên phải ép kiểu sang String
        String email =(String) session.getAttribute("email");
        this.productService.hanldeAddProductToCart(email, productId, session); 
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(){
        return "client/cart/show";
    }

}
