package in.codecraftsbysanta.productcatalogservice.services;

import in.codecraftsbysanta.productcatalogservice.dtos.UserDTO;
import in.codecraftsbysanta.productcatalogservice.models.Product;
import in.codecraftsbysanta.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("sps")
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Product getProductById(Long productId) {

        Optional<Product> productOptional = productRepo.findProductById(productId);
        if(productOptional.isEmpty())
            return null;
        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepo.findAll();

    }

    @Override
    public Product replaceProduct(Long productId, Product request) {

        Optional<Product> productOptional = productRepo.findProductById(productId);
        if (productOptional.isEmpty()) {
            return null;
        }

        Product existingProduct = productOptional.get();
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setImgUrl(request.getImgUrl());
        existingProduct.setCategory(request.getCategory());
        return productRepo.save(existingProduct);

    }

    @Override
    public Product save(Product product) {

        return productRepo.save(product);

    }

    @Override
    public Product getProductBasedOnUser(Long productId, Long userId) {
        Optional<Product> productOptional = productRepo.findProductById(productId);
        UserDTO userDTO = restTemplate.getForEntity("http://userservice/users/{userId}", UserDTO.class,userId).getBody();
        System.out.println(userDTO.getEmail());

        if(userDTO != null) {
            return productOptional.get();
        }

        return  null;
    }
}