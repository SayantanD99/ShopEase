package in.codecraftsbysanta.userauthservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Role extends BaseModel{

    @Column(unique = true, nullable = false)
    private String value;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}