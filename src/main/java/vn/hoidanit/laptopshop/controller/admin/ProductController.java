package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.hoidanit.laptopshop.domain.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ProductController {

    @GetMapping("/admin/product")
    public String getProductForAdminPage(){
        return "/admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getMethodName(Model model) {
        model.addAttribute("newProduct", new Product());
        return "/admin/product/create";
    }

    // @PostMapping("/admin/product/create")
    // public String postMethodName(Model model, @ModelAttribute("newProduct") Product laptop, @RequestParam("productFile") MultipartFile file) {
        
        
    //     return entity;
    // }
    
    
}
