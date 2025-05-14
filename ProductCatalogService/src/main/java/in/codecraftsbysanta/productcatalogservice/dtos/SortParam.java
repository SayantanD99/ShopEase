package in.codecraftsbysanta.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SortParam {
    private SortType sortType;
    private String sortCriteria; //"id" or "price"

}
