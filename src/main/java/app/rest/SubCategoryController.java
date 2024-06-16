package app.rest;

import app.models.SubCategory;
import app.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class SubCategoryController {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @GetMapping("/subCategory")
    public ResponseEntity<List<SubCategory>> getSubCategory() {
        List<SubCategory> subCategories = subCategoryRepository.findAllSorted();
        if (subCategories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subCategories);
    }
}
