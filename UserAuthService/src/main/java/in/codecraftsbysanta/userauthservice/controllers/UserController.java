package in.codecraftsbysanta.userauthservice.controllers;

import in.codecraftsbysanta.userauthservice.dtos.UserDTO;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User user = userService.getUserDetails(id);
        if(user == null)
            return null;
        return from(user);
    }

    public UserDTO from(User user) {
        UserDTO UserDTO = new UserDTO();
        UserDTO.setId(user.getId());
        UserDTO.setEmail(user.getEmail());
        //UserDTO.setRoles(user.getRoles());
        return UserDTO;
    }
}