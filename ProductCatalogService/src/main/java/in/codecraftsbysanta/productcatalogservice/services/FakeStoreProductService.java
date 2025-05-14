package in.codecraftsbysanta.productcatalogservice.services;

import in.codecraftsbysanta.productcatalogservice.clients.FakeStoreApiClient;
import in.codecraftsbysanta.productcatalogservice.dtos.FakeStoreProductDTO;
import in.codecraftsbysanta.productcatalogservice.models.Category;
import in.codecraftsbysanta.productcatalogservice.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fkps")
@Primary
public class FakeStoreProductService implements IProductService{

    @Autowired
    public RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public Product getProductById(Long productId) {

        FakeStoreProductDTO fakeStoreProductDTO = null;
        fakeStoreProductDTO = (FakeStoreProductDTO) redisTemplate.opsForHash().get("products", productId);

        if(fakeStoreProductDTO == null) {

            System.out.println("Got from Fakestore API");
            fakeStoreProductDTO = fakeStoreApiClient.getProductById(productId);
            redisTemplate.opsForHash().put("products", productId, fakeStoreProductDTO);

        }
        else{

            System.out.println("Fetched from Redis");
        }

        return from(fakeStoreProductDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO[]> listResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);
        for (FakeStoreProductDTO fakeStoreProductDTO : listResponseEntity.getBody()) {
            products.add(from(fakeStoreProductDTO));
        }
        return products;
    }

    @Override
    public Product replaceProduct(Long productId, Product request) {
        FakeStoreProductDTO fakeStoreProductDtoRequest = from(request);
        FakeStoreProductDTO response =
                requestForEntity("http://fakestoreapi.com/products/{productId}",HttpMethod.PUT,
                        fakeStoreProductDtoRequest, FakeStoreProductDTO.class,productId).getBody();
        return from(response);
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product getProductBasedOnUser(Long productId, Long userId) {
        return null;
    }

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private FakeStoreProductDTO from(Product product) {

        FakeStoreProductDTO fakeStoreProductDto = new FakeStoreProductDTO();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImgUrl());

        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }

        return fakeStoreProductDto;

    }

    private Product from(FakeStoreProductDTO fakeStoreProductDto) {

        Product product = new Product();

        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImgUrl(fakeStoreProductDto.getImage());

        Category category = new Category();

        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }
}
