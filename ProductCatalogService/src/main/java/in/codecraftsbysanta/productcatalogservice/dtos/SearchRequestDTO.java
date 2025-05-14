package in.codecraftsbysanta.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchRequestDTO {
    int pageSize;
    int pageNumber;
    String searchQuery;
    List<SortParam> sortParams = new ArrayList<>();
}
