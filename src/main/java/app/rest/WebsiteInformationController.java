package app.rest;

import app.models.WebsiteImage;
import app.models.WebsiteInformation;
import app.repositories.WebsiteInformationRepository;
import app.repositories.WebsiteImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;

@RestController
@Transactional
public class WebsiteInformationController {

    @Autowired
    private WebsiteInformationRepository websiteInformationRepository;

    @Autowired
    private WebsiteImageRepository websiteImageRepository;

    @GetMapping("/websiteInformation/{pageName}")
    public ResponseEntity<WebsiteInformation> getWebsiteInformation(@PathVariable String pageName) {
        try {
            WebsiteInformation response = websiteInformationRepository.getWebsiteInformationByPageName(pageName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/admin/websiteInformation/{pageName}")
    public ResponseEntity<WebsiteInformation> createWebsiteInformation(@PathVariable String pageName ,@RequestBody String data) {
        WebsiteInformation response = websiteInformationRepository.save(new WebsiteInformation(pageName, data));
        return ResponseEntity.ok(response);
    }

    @PutMapping("admin/websiteInformation/{uuid}")
    public ResponseEntity<WebsiteInformation> updateWebsiteInformation(@PathVariable String uuid, @RequestBody String data) {
        try {
            WebsiteInformation websiteInformation = websiteInformationRepository.findByUuid(uuid);
            websiteInformation.setData(data);
            WebsiteInformation response = websiteInformationRepository.update(websiteInformation);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("admin/websiteInformation/{uuid}/image")
    public ResponseEntity<WebsiteInformation> updateWebsiteInformationImages(@PathVariable String uuid, @RequestParam("imageFile") MultipartFile file){
        try {
            //find the information
            WebsiteInformation websiteInformation = websiteInformationRepository.findByUuid(uuid);

            if (websiteInformation == null) {
                return ResponseEntity.notFound().build();
            }

            WebsiteImage newImage = new WebsiteImage(Base64.getEncoder().encodeToString(file.getBytes()));

            // remove the old image
            if (websiteInformation.getImages() != null) {
                if (!websiteInformation.getImages().isEmpty()) {
                    WebsiteImage oldImage = websiteInformation.getImages().get(0);
                    websiteInformation.removeImage(oldImage);
                    websiteImageRepository.delete(oldImage);
                }
            }

//            add the new image
            newImage.setWebsiteInformation(websiteInformation);
            newImage = websiteImageRepository.save(newImage);
            websiteInformation.setImages(new ArrayList<>());
            websiteInformation.addImage(newImage);
            WebsiteInformation response = websiteInformationRepository.save(websiteInformation);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
