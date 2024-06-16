package app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Sector {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @JsonView(view.Sector.class)
    private String title;

    @JsonView(view.Sector.class)
    private String best_organisation;

    @JsonView(view.Sector.class)
    private String person_of_intrest;

    @JsonView(view.Sector.class)
    private String researcher;

    @Column(columnDefinition = "text")
    @JsonView(view.Sector.class)
    private String link;

    @OneToMany(mappedBy = "sector", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    @JsonManagedReference(value = "users")
    private List<User> users = new ArrayList<>();

    public Sector() {
    }

    public Sector(String title, String best_organisation, String person_of_intrest, String researcher, String link) {
        this.title = title;
        this.best_organisation = best_organisation;
        this.person_of_intrest = person_of_intrest;
        this.researcher = researcher;
        this.link = link;
    }
    public Sector(String title, String best_organisation, String person_of_intrest, String researcher, String link, String uuid) {
        this.title = title;
        this.best_organisation = best_organisation;
        this.person_of_intrest = person_of_intrest;
        this.researcher = researcher;
        this.link = link;
        this.uuid = uuid;
    }

    public Sector(String title) {
        this.title = title;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBest_organisation() {
        return best_organisation;
    }

    public void setBest_organisation(String best_origination) {
        this.best_organisation = best_origination;
    }

    public String getPerson_of_intrest() {
        return person_of_intrest;
    }

    public void setPerson_of_intrest(String person_of_intrest) {
        this.person_of_intrest = person_of_intrest;
    }

    public String getResearcher() {
        return researcher;
    }

    public void setResearcher(String researcher) {
        this.researcher = researcher;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", person_of_intrest='" + person_of_intrest + '\'' +
                ", researcher='" + researcher + '\'' +
                ", link='" + link + '\'' +
                ", users=" + users +
                '}';
    }
}
