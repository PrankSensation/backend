package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
public class Answer {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @Column(columnDefinition = "text")
    @JsonView(view.Answer.class)
    private String answer;

    @JsonView(view.Answer.class)
    private int points;

    @Column(columnDefinition = "text")
    @JsonView(view.Answer.class)
    private String tip;

    @JsonView(view.Answer.class)
    private int answerNumber;

    @ManyToOne()
    @JsonBackReference(value = "question")
    @JsonView(view.Answer.class)
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.PERSIST)
    private List<Result> results;

    public Answer() {
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public Answer(String answer, int points, String tip, int answerNumber) {
        this.answer = answer;
        this.points = points;
        this.tip = tip;
        this.answerNumber = answerNumber;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String name) {
        this.answer = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer answer)) return false;

        return this.uuid.equals(answer.uuid);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer_id=" + uuid +
                ", answer='" + answer + '\'' +
                ", points=" + points +
                ", tip='" + tip + '\'' +
                ", answer_number=" + answerNumber +
                ", question=" + question.getQuestion() +
                '}';
    }
}
