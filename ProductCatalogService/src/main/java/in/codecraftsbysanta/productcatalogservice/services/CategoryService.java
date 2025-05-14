package in.codecraftsbysanta.productcatalogservice.services;

import in.codecraftsbysanta.productcatalogservice.models.Category;
import in.codecraftsbysanta.productcatalogservice.repos.CategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepo.findCategoryById(categoryId);
        if(categoryOptional.isEmpty())
            return null;
        return categoryOptional.get();
    }


    @Override
    public List<Category> getAllCategories() {

        return categoryRepo.findAll();

    }

    @Override
    public Category replaceCategory(Long categoryId, Category request) {

        Optional<Category> categoryOptional = categoryRepo.findCategoryById(categoryId);
        if (categoryOptional.isEmpty()) {
            return null;
        }

        Category existingCategory = categoryOptional.get();
        existingCategory.setName(request.getName());
        existingCategory.setDescription(request.getDescription());
        return categoryRepo.save(existingCategory);

    }

    @Override
    public Category save(Category category) {

        return categoryRepo.save(category);

    }
    
}