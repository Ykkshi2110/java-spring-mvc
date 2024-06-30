package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getTableUsers(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users1", users); // key,value
        return "/admin/user/table-user";
    }

    @GetMapping("/admin/user/{id}")
    public String getDetailUsersPage(Model model, @PathVariable("id") long id) {
        User user_detail = this.userService.getDetailUserById(id);
        model.addAttribute("user_detail", user_detail);
        return "/admin/user/detail-user";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable("id") long id) {
        User currentUser = this.userService.getDetailUserById(id);
        model.addAttribute("updateUser", currentUser);
        return "/admin/user/update-user";
    }

    // Lấy giá trị trực tiếp từ view thì sử dụng Annotation ModelAttribute
    @PostMapping("admin/user/update")
    public String handleUpdateUser(Model model, @ModelAttribute("updateUser") User peter) {
        User currentUser = this.userService.getDetailUserById(peter.getId());
        if (currentUser != null) {
            currentUser.setAddress(peter.getAddress());
            currentUser.setFullName(peter.getFullName());
            currentUser.setPhone(peter.getPhone());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("deleteUser", new User());
        return "/admin/user/delete-user";
    }

    @PostMapping("admin/user/delete")
    public String handleDeleteUser(Model model, @ModelAttribute("deleteUser") User peter) {
        this.userService.handleDeleteUserById(peter.getId());
        return "redirect:/admin/user";
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
