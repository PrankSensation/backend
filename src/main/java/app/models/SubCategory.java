package app.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SubCategory {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @JsonView(view.Category.class)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(view.Category.class)
    @JsonIgnoreProperties("subCategories")
    private Category category;

    @OneToMany(mappedBy = "questionCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("questionCategory")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Question> questions = new ArrayList<>();

    public SubCategory() {
    }

    public SubCategory(String uuid, String title, Category category) {
        this.uuid = uuid;
        this.title = title;
        this.category = category;
    }

    public SubCategory(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
