package in.codecraftsbysanta.userauthservice.services;

import in.codecraftsbysanta.userauthservice.exceptions.RoleNotFoundException;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.Role;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.repos.RoleRepo;
import in.codecraftsbysanta.userauthservice.repos.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_validRole_savesUser() throws RoleNotFoundException {
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setValue("USER");
        when(roleRepo.findByValue("USER")).thenReturn(Optional.of(userRole));
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(Set.of(userRole));
        when(userRepo.save(any(User.class))).thenReturn(user);

        User saved = userService.registerUser("test@example.com", "password");
        assertEquals("test@example.com", saved.getEmail());
        assertTrue(saved.getRoles().contains(userRole));
    }

    @Test
    void registerUser_roleNotFound_throwsException() {
        when(roleRepo.findByValue("USER")).thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class, () -> userService.registerUser("test@example.com", "password"));
    }

    @Test
    void getUserDetails_validId_returnsUser() throws UserNotRegisteredException {
        User user = new User();
        user.setId(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserDetails(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getUserDetails_invalidId_throwsException() {
        when(userRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(UserNotRegisteredException.class, () -> userService.getUserDetails(2L));
    }
}