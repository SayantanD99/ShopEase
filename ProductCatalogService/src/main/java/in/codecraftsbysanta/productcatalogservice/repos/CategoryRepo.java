package in.codecraftsbysanta.productcatalogservice.repos;

import in.codecraftsbysanta.productcatalogservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Optional<Category> findCategoryById(Long id);
    Category save(Category c);
    List<Category> findAll();
}
