package in.codecraftsbysanta.productcatalogservice.repos;

import in.codecraftsbysanta.productcatalogservice.models.Category;
import in.codecraftsbysanta.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    @Transactional
    void testFetchTypes() {
        Category category = categoryRepo.findById(22L).get();
        for(Product product  : category.getProducts()) {
            System.out.println(product.getName());
        }
    }


    @Test
    @Transactional
    void testSomething() {
        List<Category> categoryList = categoryRepo.findAll();
        for(Category category :  categoryList) {
            for(Product p : category.getProducts()) {
                System.out.println(p.getName());
            }
        }
    }
}