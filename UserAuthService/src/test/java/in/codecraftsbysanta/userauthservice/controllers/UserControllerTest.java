package in.codecraftsbysanta.userauthservice.controllers;

import in.codecraftsbysanta.userauthservice.dtos.UserDTO;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.Role;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_valid_returnsUserDTO() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Role role = new Role();
        role.setId(1L);
        role.setValue("USER");

        user.setRoles(Set.of(role));
        when(userService.getUserDetails(1L)).thenReturn(user);
        UserDTO dto = userController.getUserById(1L);
        assertEquals(1L, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("USER", dto.getRoles().iterator().next().getValue());
    }

    @Test
    void getUserById_invalid_throwsException() throws Exception {
        when(userService.getUserDetails(2L)).thenThrow(UserNotRegisteredException.class);
        assertThrows(UserNotRegisteredException.class, () -> userController.getUserById(2L));
    }
}