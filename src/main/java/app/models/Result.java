package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Result {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @ManyToOne
    @JsonBackReference(value = "attempt")
    @JsonView(view.Result.class)
    private Attempt attempt;

    @ManyToOne
    @JsonView(view.Result.class)
    @JsonBackReference(value = "answer")
    private Answer answer;

    @ManyToOne
    @JsonView(view.Result.class)
    @JsonBackReference(value = "question")
    private Question question;

    @JsonView(view.Result.class)
    private String result;


    public Result() {
    }

    public Result(String result) {
        this.result = result;
    }

    public Result(Answer answer, String result, Attempt attempt) {
        this.answer = answer;
        this.result = result;
        this.attempt = attempt;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUuid() {
        return uuid;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Attempt getAttempt() {
        return attempt;
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result result)) return false;

        return uuid.equals(result.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }


}
