package in.codecraftsbysanta.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String email;
    private Set<RoleDTO> roles;


}
