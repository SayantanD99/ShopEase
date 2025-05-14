package in.codecraftsbysanta.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreProductDTO implements Serializable {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
