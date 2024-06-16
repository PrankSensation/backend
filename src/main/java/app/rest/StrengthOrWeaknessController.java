package app.rest;

import app.models.StrengthOrWeakness;
import app.repositories.StrengthOrWeaknessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StrengthOrWeaknessController {

    @Autowired
    StrengthOrWeaknessRepository strengthOrWeaknessRepository;

    @GetMapping("/admin/strengthWeakness")
    public ResponseEntity<List<StrengthOrWeakness>> getAllStrengthWeaknessAndCategories() {
        List<StrengthOrWeakness> strengthWeaknessList = strengthOrWeaknessRepository.findAllCategories();
        if (strengthWeaknessList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(strengthWeaknessList);
    }

    @PutMapping("/admin/strengthWeakness")
    public ResponseEntity<StrengthOrWeakness> updateStrengthWeakness(@RequestBody StrengthOrWeakness strengthWeakness) {
        StrengthOrWeakness response = strengthOrWeaknessRepository.update(strengthWeakness);

        return ResponseEntity.ok(response);
    }
}
