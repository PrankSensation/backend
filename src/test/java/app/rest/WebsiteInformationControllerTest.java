package app.rest;

import app.models.WebsiteImage;
import app.models.WebsiteInformation;
import app.repositories.WebsiteImageRepository;
import app.repositories.WebsiteInformationRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class WebsiteInformationControllerTest {
    @Mock
    private WebsiteInformationRepository websiteInformationRepository;

    @Mock
    private WebsiteImageRepository websiteImageRepository;

    @InjectMocks
    private WebsiteInformationController websiteInformationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWebsiteInformation_Success() {
        String pageName = "home";
        WebsiteInformation websiteInformation = new WebsiteInformation(pageName, "data");

        when(websiteInformationRepository.getWebsiteInformationByPageName(pageName)).thenReturn(websiteInformation);

        ResponseEntity<WebsiteInformation> response = websiteInformationController.getWebsiteInformation(pageName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(websiteInformation, response.getBody());
    }

    @Test
    public void testGetWebsiteInformation_NotFound() {
        String pageName = "nonexistent";

        when(websiteInformationRepository.getWebsiteInformationByPageName(pageName)).thenThrow(new RuntimeException());

        ResponseEntity<WebsiteInformation> response = websiteInformationController.getWebsiteInformation(pageName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateWebsiteInformation_Success() {
        String pageName = "home";
        String data = "some data";
        WebsiteInformation websiteInformation = new WebsiteInformation(pageName, data);

        when(websiteInformationRepository.save(any(WebsiteInformation.class))).thenReturn(websiteInformation);

        ResponseEntity<WebsiteInformation> response = websiteInformationController.createWebsiteInformation(pageName, data);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(websiteInformation, response.getBody());
    }

    @Test
    public void testUpdateWebsiteInformation_Success() {
        String uuid = "uuid";
        String data = "updated data";
        WebsiteInformation websiteInformation = new WebsiteInformation("home", "old data");
        websiteInformation.setUuid(uuid);

        when(websiteInformationRepository.findByUuid(uuid)).thenReturn(websiteInformation);
        when(websiteInformationRepository.update(any(WebsiteInformation.class))).thenReturn(websiteInformation);

        ResponseEntity<WebsiteInformation> response = websiteInformationController.updateWebsiteInformation(uuid, data);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(websiteInformation, response.getBody());
        assertEquals(data, response.getBody().getData());
    }

    @Test
    public void testUpdateWebsiteInformation_NotFound() {
        String uuid = "nonexistent";
        String data = "updated data";

        when(websiteInformationRepository.findByUuid(uuid)).thenThrow(new RuntimeException());

        ResponseEntity<WebsiteInformation> response = websiteInformationController.updateWebsiteInformation(uuid, data);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateWebsiteInformationImages_Success() throws Exception {
        String uuid = UUID.randomUUID().toString();
        WebsiteInformation websiteInformation = new WebsiteInformation("home", "old data");
        websiteInformation.setUuid(uuid);

        MockMultipartFile file = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", "image data".getBytes());
        WebsiteImage newImage = new WebsiteImage(Base64.getEncoder().encodeToString(file.getBytes()));
        newImage.setUuid(UUID.randomUUID().toString());

        when(websiteInformationRepository.findByUuid(uuid)).thenReturn(websiteInformation);
        when(websiteImageRepository.save(any(WebsiteImage.class))).thenReturn(newImage);
        when(websiteInformationRepository.save(any(WebsiteInformation.class))).thenReturn(websiteInformation);

        ResponseEntity<WebsiteInformation> response = websiteInformationController.updateWebsiteInformationImages(uuid, file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getImages().isEmpty());
        assertEquals(Base64.getEncoder().encodeToString(file.getBytes()), response.getBody().getImages().get(0).getImage());
    }

    @Test
    public void testUpdateWebsiteInformationImages_NotFound() throws Exception {
        String uuid = "nonexistent";
        MockMultipartFile file = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", "image data".getBytes());

        when(websiteInformationRepository.findByUuid(uuid)).thenReturn(null);

        ResponseEntity<WebsiteInformation> response = websiteInformationController.updateWebsiteInformationImages(uuid, file);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
