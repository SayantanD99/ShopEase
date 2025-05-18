package in.codecraftsbysanta.userauthservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.codecraftsbysanta.userauthservice.clients.KafkaProducerClient;
import in.codecraftsbysanta.userauthservice.dtos.EmailDTO;
import in.codecraftsbysanta.userauthservice.exceptions.RoleNotFoundException;
import in.codecraftsbysanta.userauthservice.exceptions.UserAlreadyExistsException;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.Role;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.repos.RoleRepo;
import in.codecraftsbysanta.userauthservice.repos.UserRepo;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaProducerClient kafkaProducerClient;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp_valid_returnsUser() throws Exception {
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Role role = new Role();
        role.setValue("USER");
        when(roleRepo.findByValue("USER")).thenReturn(Optional.of(role));
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        when(objectMapper.writeValueAsString(any(EmailDTO.class))).thenReturn("mockedJson");
        doNothing().when(kafkaProducerClient).sendMessage(eq("signup"), anyString());

        User user = authService.signUp("test@example.com", "password");

        assertEquals("test@example.com", user.getEmail());
        assertTrue(user.getRoles().contains(role));
        verify(bCryptPasswordEncoder).encode("password");
        verify(objectMapper).writeValueAsString(any(EmailDTO.class));
        verify(kafkaProducerClient).sendMessage(eq("signup"), anyString());
    }

    @Test
    void signUp_duplicateEmail_throwsException() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> authService.signUp("test@example.com", "password"));
    }

    @Test
    void login_unregistered_throwsException() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotRegisteredException.class, () -> authService.login("test@example.com", "password"));
    }
}