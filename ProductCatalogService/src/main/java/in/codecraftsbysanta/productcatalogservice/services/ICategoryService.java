package in.codecraftsbysanta.productcatalogservice.services;

import in.codecraftsbysanta.productcatalogservice.models.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategories();
    Category replaceCategory(Long categoryId, Category request);
    Category save(Category category);
}
