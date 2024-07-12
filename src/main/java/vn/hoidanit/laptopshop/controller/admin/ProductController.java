package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;


@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductForAdminPage(Model model) {
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("products1", products); // key, value
        return "/admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getMethodName(Model model) {
        model.addAttribute("newProduct", new Product());
        return "/admin/product/create";
    }

    @GetMapping("/admin/product/{id}")
    public String getDetailProductPage(Model model, @PathVariable("id") long id) {
        Product myPro = this.productService.fetchProductById(id).get();
        model.addAttribute("productDetail", myPro);
        return "/admin/product/detail";
    }

    @PostMapping("/admin/product/create")
    public String getCreateProductPage(Model model, @ModelAttribute("newProduct") @Valid Product laptop,
            BindingResult newProductBindingResult, @RequestParam("productFile") MultipartFile file) {
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        // validate
        if (newProductBindingResult.hasErrors()){
            return "/admin/product/create";
        }

        String imgProduct = this.uploadService.handleSaveUploadFile(file, "product");
        laptop.setImage(imgProduct);
        this.productService.handleSaveProduct(laptop);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable("id") long id) {
        Optional<Product> myPro = this.productService.getProductById(id);
        model.addAttribute("newProduct", myPro.get());
        return "/admin/product/update";
    }
    
    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product currentProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currentProduct != null) {
            // update new image
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setFactory(pr.getFactory());
            currentProduct.setTarget(pr.getTarget());

            this.productService.handleSaveProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("deleteProduct", new Product());
        return "/admin/product/delete";
    }

    @PostMapping("admin/product/delete")
    public String handleDeleteUser(Model model, @ModelAttribute("deleteProduct") Product myProduct) {
        this.productService.handleDeleteProductById(myProduct.getId());
        return "redirect:/admin/product";
    }
    

}
