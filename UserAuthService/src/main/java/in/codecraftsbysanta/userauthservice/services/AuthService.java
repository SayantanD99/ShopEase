package in.codecraftsbysanta.userauthservice.services;

import in.codecraftsbysanta.userauthservice.clients.KafkaProducerClient;
import in.codecraftsbysanta.userauthservice.dtos.EmailDTO;
import in.codecraftsbysanta.userauthservice.exceptions.PasswordMismatchException;
import in.codecraftsbysanta.userauthservice.exceptions.UserAlreadyExistsException;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.Role;
import in.codecraftsbysanta.userauthservice.models.Session;
import in.codecraftsbysanta.userauthservice.models.Status;
import in.codecraftsbysanta.userauthservice.models.User;
import in.codecraftsbysanta.userauthservice.repos.SessionRepo;
import in.codecraftsbysanta.userauthservice.repos.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.antlr.v4.runtime.misc.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private KafkaProducerClient kafkaProducerClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User signUp(String email, String password) throws UserAlreadyExistsException {

        Optional<User> user = userRepo.findByEmail(email);

        if(user.isPresent()){

            throw new UserAlreadyExistsException("User already exists with email. Please login");

        }

        User newUser = new User();

        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setCreatedAt(new Date());
        newUser.setLastUpdatedAt(new Date());

        Role role = new Role();

        role.setValue("USER");

        List<Role> roles = new ArrayList<>();

        roles.add(role);

        newUser.setRoles(roles);

        userRepo.save(newUser);

        //Send message into kafka
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setTo(email);
            emailDTO.setFrom("email@gmail.co");
            emailDTO.setSubject("Welcome to ShopEasy");
            emailDTO.setBody("Have a great experience.");
            kafkaProducerClient.sendMessage("signup", objectMapper.writeValueAsString(emailDTO));
        }catch (JsonProcessingException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        return newUser;
    }

    @Override
    public Pair<User, String> login(String email, String password) throws UserNotRegisteredException, PasswordMismatchException{

        Optional<User> userOptional = userRepo.findByEmail(email);

        if(userOptional.isEmpty()){

            throw new UserNotRegisteredException("User not registered. Please signup");

        }

        String storedPassword = userOptional.get().getPassword();

        if(!bCryptPasswordEncoder.matches(password, storedPassword)){

            throw new PasswordMismatchException("Password mismatch. Please try again");

        }

        Map<String,Object> payload = new HashMap<>();

        Long nowInMillis = System.currentTimeMillis();

        payload.put("iat",nowInMillis);
        payload.put("exp",nowInMillis+100000);
        payload.put("userId",userOptional.get().getId());
        payload.put("iss","scaler");
        payload.put("scope",userOptional.get().getRoles());

//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = algorithm.key().build();

        String token = Jwts.builder().claims(payload).signWith(secretKey).compact();

        Session session = new Session();

        session.setToken(token);
        session.setUser(userOptional.get());
        session.setStatus(Status.ACTIVE);

        sessionRepo.save(session);

        return new Pair<User,String>(userOptional.get(),token);

    }

    //validateToken(userId, token) {
    // check if token stored in db is matching with this token or not
    // whether the token has expired or not ,
    // currentTimeStamp < expiryTimeStamp
    //In order to get expiryTimeStamp, we need to parse token and get payload(claims)
    // -> get expiry.
    //}

    public Boolean validateToken(String token,Long userId) {

        Optional<Session> optionalSession = sessionRepo.findByTokenAndUser_Id(token,userId);

        if(optionalSession.isEmpty()) {

            return false;

        }

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long tokenExpiry = (Long) claims.get("exp");

        Long currentTime = System.currentTimeMillis();

        System.out.println(tokenExpiry);
        System.out.println(currentTime);

        if(currentTime > tokenExpiry) {

            Session session = optionalSession.get();
            session.setStatus(Status.INACTIVE);
            sessionRepo.save(session);
            return false;

        }

        return true;

    }

}
