package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrsUser = this.userService.getAllUser();
        List<User> arrsUserFindByEmail = this.userService.getAllUserByEmail("quocbui2110@gmail.com");
        System.out.println(arrsUser);
        System.out.println(arrsUserFindByEmail);

        String test = this.userService.handleHello();
        model.addAttribute("peter", test);
        model.addAttribute("quocbui", "Con mẹ mày nhé thằng chó Peter");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getDetailUsers(Model model){
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users1", users); // key,value
        return "/admin/user/TableUser";
    }

    @RequestMapping("/admin/user/create") // Không khai báo method thì mặc định là GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User peter) {
        this.userService.handleSaveUser(peter);
        return "redirect:/admin/user";
    }

}
