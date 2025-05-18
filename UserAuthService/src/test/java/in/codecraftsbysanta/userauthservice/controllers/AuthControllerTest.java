package in.codecraftsbysanta.userauthservice.controllers;

import in.codecraftsbysanta.userauthservice.dtos.LoginRequest;
import in.codecraftsbysanta.userauthservice.dtos.SignUpRequest;
import in.codecraftsbysanta.userauthservice.dtos.ValidateTokenDTO;
import in.codecraftsbysanta.userauthservice.exceptions.UnauthorizedException;
import in.codecraftsbysanta.userauthservice.exceptions.UserAlreadyExistsException;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private IAuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_valid_returnsCreated() throws Exception {
        SignUpRequest req = new SignUpRequest();
        req.setEmail("test@example.com");
        req.setPassword("pass");
        User user = new User();
        user.setEmail("test@example.com");
        user.setRoles(Collections.emptySet());
        when(authService.signUp(anyString(), anyString())).thenReturn(user);
        var response = authController.signup(req);
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void signup_duplicate_returnsBadRequest() throws Exception {
        SignUpRequest req = new SignUpRequest();
        req.setEmail("duplicate@example.com");
        req.setPassword("pass");
        when(authService.signUp(anyString(), anyString())).thenThrow(new UserAlreadyExistsException("User Exists"));
        var response = authController.signup(req);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void login_valid_returnsOk() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@example.com");
        req.setPassword("pass");
        User user = new User();
        user.setEmail("test@example.com");
        user.setRoles(Collections.emptySet());
        when(authService.login(anyString(), anyString())).thenReturn(new Pair<>(user, "cookie"));
        var response = authController.login(req);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void login_unregistered_returnsNotFound() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("unregistered@example.com");
        req.setPassword("pass");
        when(authService.login(anyString(), anyString())).thenThrow(new UserNotRegisteredException("User Not found"));
        var response = authController.login(req);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void validateToken_valid_returnsTrue() throws Exception {
        ValidateTokenDTO dto = new ValidateTokenDTO();
        dto.setToken("validToken");
        dto.setUserId(1L);
        when(authService.validateToken(anyString(), anyLong())).thenReturn(true);
        assertTrue(authController.validateToken(dto));
    }

    @Test
    void validateToken_invalid_throwsUnauthorized() throws Exception {
        ValidateTokenDTO dto = new ValidateTokenDTO();
        dto.setToken("invalidToken");
        dto.setUserId(1L);
        when(authService.validateToken(anyString(), anyLong())).thenReturn(false);
        assertThrows(UnauthorizedException.class, () -> authController.validateToken(dto));
    }
}