package in.codecraftsbysanta.productcatalogservice.controllers;

import in.codecraftsbysanta.productcatalogservice.dtos.CategoryDTO;
import in.codecraftsbysanta.productcatalogservice.dtos.ProductDTO;
import in.codecraftsbysanta.productcatalogservice.models.Category;
import in.codecraftsbysanta.productcatalogservice.models.Product;
import in.codecraftsbysanta.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    @Qualifier("sps")
    private IProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDtos = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for(Product product : products) {
            productDtos.add(from(product));
        }
        return productDtos;
    }

    @GetMapping("{pid}/{uid}")
    public ProductDTO getProductDetailsBasedOnUserRole(@PathVariable Long pid,@PathVariable Long uid) {
        Product product = productService.getProductBasedOnUser(pid,uid);
        if(product == null) return  null;
        return from(product);
    }

    @GetMapping("{productID}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable("productID") Long productID) {
        try {
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                if (productID <= 0) {
                    headers.add("Invalid Call", "Incorrect");
                    //return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
                    throw new IllegalArgumentException("Please try with Product ID greater than 0");
                }
            Product product = productService.getProductById(productID);
            headers.add("Valid Call", "Correct");
            if (product == null)
                return new ResponseEntity<>(null, new HttpHeaders(headers), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(from(product), headers, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw exception;

        }
    }
    private ProductDTO from (Product product) {

        ProductDTO productDto = new ProductDTO();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImgUrl(product.getImgUrl());

        if(product.getCategory() != null) {
            CategoryDTO categoryDto = new CategoryDTO();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        Product input = from(productDTO);
        Product output = productService.save(input);
        return from(output);
    }

    private Product from(ProductDTO productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImgUrl(productDto.getImgUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }

    @PutMapping("/{productID}")
    public ProductDTO replaceProduct(@PathVariable("productID") Long productID, @RequestBody ProductDTO request) {
        Product product = productService.replaceProduct(productID, from(request));
        return from(product);
    }
}
