package in.codecraftsbysanta.userauthservice.services;

import in.codecraftsbysanta.userauthservice.exceptions.PasswordMismatchException;
import in.codecraftsbysanta.userauthservice.exceptions.UserAlreadyExistsException;
import in.codecraftsbysanta.userauthservice.exceptions.UserNotRegisteredException;
import in.codecraftsbysanta.userauthservice.models.User;
import org.antlr.v4.runtime.misc.Pair;

public interface IAuthService {

    User signUp(String email, String password) throws UserAlreadyExistsException;

    Pair<User,String> login(String email, String password) throws UserNotRegisteredException, PasswordMismatchException;

    Boolean validateToken(String token,Long userId);

}
