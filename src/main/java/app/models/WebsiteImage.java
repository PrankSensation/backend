package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class WebsiteImage {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String uuid;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToOne
    @JsonBackReference
    private WebsiteInformation websiteInformation;

    public WebsiteImage() {
    }

    public WebsiteImage(String image) {
        this.image = image;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public WebsiteInformation getWebsiteInformation() {
        return websiteInformation;
    }

    public void setWebsiteInformation(WebsiteInformation websiteInformation) {
        this.websiteInformation = websiteInformation;
    }
}
