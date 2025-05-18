package in.codecraftsbysanta.userauthservice.services;

import in.codecraftsbysanta.userauthservice.exceptions.RoleNotFoundException;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.Role;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.repos.RoleRepo;
import in.codecraftsbysanta.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    public User registerUser(String email, String password) throws RoleNotFoundException{
        Role userRole = roleRepo.findByValue("USER")
                .orElseThrow(() -> new RoleNotFoundException("Role USER not found"));
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Set.of(userRole));
        return userRepo.save(user);
    }

    public User getUserDetails(Long id) throws UserNotRegisteredException {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotRegisteredException("User not found"));
    }
}