package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`attempt`")
public class Attempt {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(length = 36)
    @JsonView({view.Id.class, view.Result.class})
    private String uuid;

    @JsonView(view.Attempt.class)
    private boolean is_completed = false;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "attempt")
    @JsonView(view.Attempt.class)
    private List<Result> results = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(view.Attempt.class)
    private Questionnaire questionnaire;

    @ManyToOne
    @JsonBackReference(value = "user")
    private User user;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Attempt() {
    }

    public Attempt(Questionnaire questionnaire, User user) {
        this.questionnaire = questionnaire;
        this.user = user;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attempt)) return false;

        Attempt attempt = (Attempt) o;

        return getUuid().equals(attempt.getUuid());
    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "uuid='" + uuid + '\'' +
                ", is_completed=" + is_completed +
                ", results=" + results +
                ", questionnaire=" + questionnaire +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
