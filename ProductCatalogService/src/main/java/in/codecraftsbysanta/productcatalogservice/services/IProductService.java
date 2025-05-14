package in.codecraftsbysanta.productcatalogservice.services;

import in.codecraftsbysanta.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long productId);

    List<Product> getAllProducts();

    Product replaceProduct(Long productId, Product request);

    Product save(Product product);

    Product getProductBasedOnUser(Long productId,Long userId);

}
