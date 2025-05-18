package in.codecraftsbysanta.userauthservice.controllers;

import in.codecraftsbysanta.userauthservice.dtos.RoleDTO;
import in.codecraftsbysanta.userauthservice.dtos.UserDTO;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) throws UserNotRegisteredException {
        User user = userService.getUserDetails(id);
        if(user == null)
            return null;
        return from(user);
    }

    public UserDTO from(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());

        Set<RoleDTO> roleDTOs = user.getRoles().stream().map(role -> {
            RoleDTO dto = new RoleDTO();
            dto.setId(role.getId());
            dto.setValue(role.getValue());
            return dto;
        }).collect(Collectors.toSet());

        userDTO.setRoles(roleDTOs);

        return userDTO;

    }
}