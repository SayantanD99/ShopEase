package in.codecraftsbysanta.userauthservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> roles= new ArrayList<>();

}
