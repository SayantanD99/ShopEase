package in.codecraftsbysanta.productcatalogservice.services;

import in.codecraftsbysanta.productcatalogservice.dtos.SortParam;
import in.codecraftsbysanta.productcatalogservice.models.Product;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {

    Page<Product> searchProducts(String searchQuery, int pageSize, int pageNumber, List<SortParam> sortParams);
}
