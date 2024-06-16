package app.rest;

import app.models.Category;
import app.models.SubCategory;
import app.repositories.CategoryRepository;
import app.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;


    @GetMapping("/admin/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/admin/category")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        Category newCategory = categoryRepository.findByUuid(category.getUuid());

        newCategory.setTitle(category.getTitle());
        newCategory.setThreat_law(category.getThreat_law());
        newCategory.setWeight(category.getWeight());
        newCategory.setThreat_market(category.getThreat_market());

        Category response = categoryRepository.update(newCategory);
        return ResponseEntity.ok(response);
    }
}
