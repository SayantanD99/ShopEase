package in.codecraftsbysanta.productcatalogservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private double price;
    private CategoryDTO category;
    private Boolean isPrime;
}
