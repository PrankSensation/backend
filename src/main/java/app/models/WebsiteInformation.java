package app.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
public class WebsiteInformation {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String uuid;

    @Column(unique = true)
    private String pageName;

    private String data;

    @OneToMany
    @JsonManagedReference
    private List<WebsiteImage> images;

    public WebsiteInformation() {
    }

    public WebsiteInformation(String pageName, String data) {
        this.pageName = pageName;
        this.data = data;
    }

    public void addImage(WebsiteImage image) {
        image.setWebsiteInformation(this);
        images.add(image);
    }

    public void removeImage(WebsiteImage image) {
        image.setWebsiteInformation(null);
        images.remove(image);
    }

    public void setImageAtIndex(int index, WebsiteImage image) {
        images.set(index, image);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public List<WebsiteImage> getImages() {
        return images;
    }

    public void setImages(List<WebsiteImage> images) {
        this.images = images;
    }
}
