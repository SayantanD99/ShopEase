package in.codecraftsbysanta.productcatalogservice.services;

import in.codecraftsbysanta.productcatalogservice.dtos.UserDTO;
import in.codecraftsbysanta.productcatalogservice.models.Product;
import in.codecraftsbysanta.productcatalogservice.repos.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class StorageProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StorageProductService storageProductService;

    private final String USER_SERVICE_URL_TEMPLATE = "http://userservice/users/{userId}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_ProductExists() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productRepo.findProductById(1L)).thenReturn(Optional.of(product));

        Product result = storageProductService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
    }

    @Test
    void getProductById_ProductNotExists() {
        when(productRepo.findProductById(1L)).thenReturn(Optional.empty());

        Product result = storageProductService.getProductById(1L);

        assertNull(result);
    }

    @Test
    void getAllProducts_ReturnsListOfProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        products.add(product1);

        Product product2 = new Product();
        product2.setId(2L);
        products.add(product2);

        when(productRepo.findAll()).thenReturn(products);

        List<Product> result = storageProductService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllProducts_ReturnsEmptyList() {
        when(productRepo.findAll()).thenReturn(new ArrayList<>());

        List<Product> result = storageProductService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void replaceProduct_ProductExists() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Name");

        Product newProductDetails = new Product();
        newProductDetails.setName("New Name");
        newProductDetails.setDescription("New Description");
        newProductDetails.setPrice(100.0);
        newProductDetails.setImgUrl("new_url");

        when(productRepo.findProductById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepo.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = storageProductService.replaceProduct(1L, newProductDetails);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("New Description", result.getDescription());
        assertEquals(100.0, result.getPrice());
        assertEquals("new_url", result.getImgUrl());
    }

    @Test
    void replaceProduct_ProductNotExists() {
        Product newProductDetails = new Product();
        newProductDetails.setName("New Name");

        when(productRepo.findProductById(1L)).thenReturn(Optional.empty());

        Product result = storageProductService.replaceProduct(1L, newProductDetails);

        assertNull(result);
    }

    @Test
    void saveProduct_SuccessfullySaves() {
        Product productToSave = new Product();
        productToSave.setName("Test Product");

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Test Product");

        when(productRepo.save(any(Product.class))).thenReturn(savedProduct);

        Product result = storageProductService.save(productToSave);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
    }

    @Test
    void getProductBasedOnUser_ProductAndUserExist() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        Long userId = 101L;

        when(productRepo.findProductById(1L)).thenReturn(Optional.of(product));
        when(restTemplate.getForEntity(eq(USER_SERVICE_URL_TEMPLATE), eq(UserDTO.class), eq(userId)))
                .thenReturn(new ResponseEntity<>(userDTO, HttpStatus.OK));

        Product result = storageProductService.getProductBasedOnUser(1L, userId);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getProductBasedOnUser_ProductExists_UserDoesNotExist() {
        Product product = new Product();
        product.setId(1L);
        Long userId = 101L;

        when(productRepo.findProductById(1L)).thenReturn(Optional.of(product));
        when(restTemplate.getForEntity(eq(USER_SERVICE_URL_TEMPLATE), eq(UserDTO.class), eq(userId)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        Product result = storageProductService.getProductBasedOnUser(1L, userId);

        assertNull(result);
    }

    @Test
    void getProductBasedOnUser_ProductDoesNotExist() {
        Long productId = 1L;
        Long userId = 101L;
        when(productRepo.findProductById(productId)).thenReturn(Optional.empty());

        // This mock might not be strictly necessary if the service returns early,
        // but it's good practice for completeness and to avoid NullPointer if it were called.
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        when(restTemplate.getForEntity(eq(USER_SERVICE_URL_TEMPLATE), eq(UserDTO.class), eq(userId)))
                .thenReturn(new ResponseEntity<>(userDTO, HttpStatus.OK));

        Product result = storageProductService.getProductBasedOnUser(productId, userId);
        assertNull(result); // Expect null because the product is not found
    }

    @Test
    void getProductBasedOnUser_ProductExists_RestTemplateReturnsNullUserDTO() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        Long userId = 101L;

        when(productRepo.findProductById(1L)).thenReturn(Optional.of(product));
        // Simulate RestTemplate returning an OK response but with a null body
        when(restTemplate.getForEntity(eq(USER_SERVICE_URL_TEMPLATE), eq(UserDTO.class), eq(userId)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        Product result = storageProductService.getProductBasedOnUser(1L, userId);

        assertNull(result); // Expect null because the user DTO is null
    }
}