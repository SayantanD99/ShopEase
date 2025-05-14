package in.codecraftsbysanta.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserDTO {
    private Long id;
    private String email;
    // private List<Role> roles = new ArrayList();
}