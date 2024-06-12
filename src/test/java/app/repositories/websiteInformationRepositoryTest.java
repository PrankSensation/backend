package app.repositories;

import app.models.WebsiteInformation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class websiteInformationRepositoryTest {
    @Autowired
    private WebsiteInformationRepository websiteInformationRepository;

    @Autowired
    private EntityManager entityManager;

    private WebsiteInformation websiteInformation;

    @BeforeEach
    public void setUp() {
        websiteInformation = new WebsiteInformation("home", "data");
        entityManager.persist(websiteInformation);
        entityManager.flush();
    }

    @Test
    public void testFindByUuid() {
        WebsiteInformation found = websiteInformationRepository.findByUuid(websiteInformation.getUuid());
        assertEquals(websiteInformation, found);
    }

    @Test
    public void testFindAll() {
        List<WebsiteInformation> allWebsiteInfo = websiteInformationRepository.findAll();
        assertFalse(allWebsiteInfo.isEmpty());
    }

    @Test
    public void testSave() {
        WebsiteInformation newWebsiteInformation = new WebsiteInformation("about", "about data");
        WebsiteInformation saved = websiteInformationRepository.save(newWebsiteInformation);
        assertEquals(newWebsiteInformation, saved);
    }

    @Test
    public void testUpdate() {
        websiteInformation.setData("updated data");
        WebsiteInformation updated = websiteInformationRepository.update(websiteInformation);
        assertEquals("updated data", updated.getData());
    }

    @Test
    public void testDelete() {
        websiteInformationRepository.delete(websiteInformation);
        assertThrows(EntityNotFoundException.class, () -> {
            entityManager.getReference(WebsiteInformation.class, websiteInformation.getUuid());
        });
    }

    @Test
    public void testGetWebsiteInformationByPageName() {
        WebsiteInformation found = websiteInformationRepository.getWebsiteInformationByPageName("home");
        assertEquals(websiteInformation, found);
    }
}
