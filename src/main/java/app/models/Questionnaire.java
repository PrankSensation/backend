package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Questionnaire {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @JsonView(view.Questionnaire.class)
    private String title;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(view.Questionnaire.class)
    private Date date;

    @Column(columnDefinition = "text")
    @JsonView(view.Questionnaire.class)
    private String description;

    @JsonView(view.Questionnaire.class)
    private boolean isActive;

    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("questionNumber ASC")
    @JsonManagedReference(value = "questionnaire")
    @JsonView(view.Questionnaire.class)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attempt> attempts = new ArrayList<>();

    public Questionnaire() {
    }

    public Questionnaire(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Questionnaire(String title, String description, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    // Special constructor to only retrieve a small portion of the Questionnaire
    public Questionnaire(String uuid, Timestamp date, String title, Boolean isActive) {
        this.uuid = uuid;
        this.date = new Date(date.getTime());
        this.title = title;
        this.isActive = isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setIsActive(boolean is_active) {
        this.isActive = is_active;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getUuid() {
        return uuid;
    }

    public void addQuestion(Question question){
        question.setQuestionnaire(this);
        this.questions.add(question);
    }

    public Date getDate() {
        return date;
    }

    public void removeQuestion(Question question){
        this.questions.remove(question);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Questionnaire that)) return false;

        return this.uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", questions=" + questions +
                '}';
    }
}
