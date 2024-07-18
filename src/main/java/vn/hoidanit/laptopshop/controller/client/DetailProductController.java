package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;


@Controller
public class DetailProductController {
    private final ProductService productService;

    public DetailProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getMethodName(Model model, @PathVariable long id) {
        Product myPro = this.productService.fetchProductById(id).get();
        model.addAttribute("detailPro", myPro);
        return "client/product/detail";
    }
    
}
