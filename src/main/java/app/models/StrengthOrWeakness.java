package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class StrengthOrWeakness {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    private String title;

    @Column(columnDefinition = "text")
    private String statement;

    private int level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "category")
    private Category category;

    public StrengthOrWeakness() {
    }

    public StrengthOrWeakness(String uuid, String title, String statement, int level, Category category) {
        this.uuid = uuid;
        this.title = title;
        this.statement = statement;
        this.level = level;
        this.category = category;
    }

    public StrengthOrWeakness(String title, String statement, int level) {
        this.title = title;
        this.statement = statement;
        this.level = level;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
