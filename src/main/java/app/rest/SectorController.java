package app.rest;

import app.models.Sector;
import app.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class SectorController {
    @Autowired
    private SectorRepository sectorRepository;

    @GetMapping("/sector")
    public ResponseEntity<List<Sector>> getAll() {
        List<Sector> sectors = sectorRepository.findAll();
        return ResponseEntity.ok(sectors);
    }

    @PostMapping("/admin/sector")
    public ResponseEntity<Sector> save(@RequestBody Sector sector) {
        Sector response = sectorRepository.create(sector);
        URI location = URI.create("/uuid/" + response.getUuid());

        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/admin/sector/{uuid}")
    public ResponseEntity<Sector> delete(@PathVariable String uuid) {
        sectorRepository.delete(sectorRepository.findByUuid(uuid));
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/admin/sector")
    public ResponseEntity<Sector> update(@RequestBody Sector sector) {
        Sector existingSector = sectorRepository.findByUuid(sector.getUuid());
        if (existingSector != null) {
            sector.setUsers(existingSector.getUsers());
        }
        Sector response = sectorRepository.update(sector);
        return ResponseEntity.ok(response);
    }
}
