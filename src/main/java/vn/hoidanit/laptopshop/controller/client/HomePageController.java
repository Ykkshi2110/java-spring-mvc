package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;


@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController (ProductService productService, UserService userService, PasswordEncoder passwordEncoder){
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }   

    @GetMapping("/")
    public String getHomePage(Model model){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = this.productService.getAllProduct(pageable);
        List<Product> listProducts = products.getContent();
        model.addAttribute("products1", listProducts);        
        return "client/homepage/show";
    }

        @GetMapping("/register")
        public String getRegisterPage(Model model) {
            model.addAttribute("registerUser", new RegisterDTO());
            return "client/auth/register";
        }

        // return về view thì không cần dấu / trước folder vì sẽ bị lỗi "//" gây nguy hại cho web
        @GetMapping("/login")
        public String getLoginPage() {
            return "client/auth/login";
        }

        @PostMapping("/register")
        public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO, BindingResult bindingResult) {
            if (bindingResult.hasErrors()){
                return "client/auth/register";
            }
    
            User user = this.userService.registerDTOtoUser(registerDTO);
            String hashPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPassword);
            user.setRole(this.userService.getRoleByName("ADMIN"));
            this.userService.handleSaveUser(user);  
            return "redirect:/login";
        }

        @GetMapping("/accessDenied")
        public String getAccessDeniedPage() {
            return "client/auth/deny";
        }

        
}
