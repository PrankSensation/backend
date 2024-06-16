package app.rest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import app.models.Sector;
import app.repositories.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class SectorControllerTest {

    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private SectorController sectorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Arrange
        Sector sector1 = new Sector();
        Sector sector2 = new Sector();
        when(sectorRepository.findAll()).thenReturn(Arrays.asList(sector1, sector2));

        // Act
        ResponseEntity<List<Sector>> response = sectorController.getAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(sectorRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        Sector sector = new Sector();
        sector.setUuid(UUID.randomUUID().toString());
        when(sectorRepository.create(any(Sector.class))).thenReturn(sector);

        // Act
        ResponseEntity<Sector> response = sectorController.save(sector);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(sector, response.getBody());
        verify(sectorRepository, times(1)).create(sector);
    }

    @Test
    public void testDelete() {
        // Arrange
        String uuid = UUID.randomUUID().toString();
        Sector sector = new Sector();
        when(sectorRepository.findByUuid(uuid)).thenReturn(sector);

        // Act
        ResponseEntity<Sector> response = sectorController.delete(uuid);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(sectorRepository, times(1)).findByUuid(uuid);
        verify(sectorRepository, times(1)).delete(sector);
    }

    @Test
    public void testUpdate() {
        // Arrange
        Sector sector = new Sector();
        when(sectorRepository.update(any(Sector.class))).thenReturn(sector);

        // Act
        ResponseEntity<Sector> response = sectorController.update(sector);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sector, response.getBody());
        verify(sectorRepository, times(1)).update(sector);
    }
}
