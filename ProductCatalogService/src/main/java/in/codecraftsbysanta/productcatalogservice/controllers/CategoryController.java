package in.codecraftsbysanta.productcatalogservice.controllers;

import in.codecraftsbysanta.productcatalogservice.dtos.CategoryDTO;
import in.codecraftsbysanta.productcatalogservice.models.Category;
import in.codecraftsbysanta.productcatalogservice.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategories();
    }
    @GetMapping("/categories/{categoryID}")
    public Category getProductById (@PathVariable("categoryID") Long categoryID){
        return categoryService.getCategoryById(categoryID);
    }
    @PostMapping("/categories")
    public void createCategory(@RequestBody Category category){
        categoryService.save(category);
    }

    @PutMapping("/categories/{categoryID}")
    public CategoryDTO updateById(@PathVariable("categoryID") Long categoryID, @RequestBody CategoryDTO request){
        Category category = categoryService.replaceCategory(categoryID, from(request));
        return from(category);
    }

    private Category from(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    private CategoryDTO from(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }


}
