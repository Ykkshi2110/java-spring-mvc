package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User handleSaveUser(User user){
        User peter = this.userRepository.save(user);
        return peter;
    }

    public User getDetailUserById(long id){
        return this.userRepository.findById(id);
    }

    public void handleDeleteUserById(long id){
         this.userRepository.deleteById(id);
    }

    // public String handleHello() {
    //     return "Hello from service";
    // }

    public Role getRoleByName(String name){
        return this.roleRepository.findByName(name);
    }
}
