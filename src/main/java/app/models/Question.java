package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @Column(columnDefinition = "text")
    @JsonView(view.Question.class)
    private String question;

    @JsonView(view.Question.class)
    private String explanation;

    @JsonView(view.Question.class)
    private int questionNumber;

    @JsonView(view.Question.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("questions")
    private SubCategory questionCategory;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("answerNumber ASC")
    @JsonManagedReference(value = "question")
    @JsonView(view.Question.class)
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne
    @JsonBackReference(value = "questionnaire")
    @JsonView(view.Question.class)
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<Result> results;

    public Question() {
    }

    public Question(String question, int questionNumber, SubCategory questionCategory) {
        this.question = question;
        this.questionNumber = questionNumber;
        this.questionCategory = questionCategory;
    }

    public Question(String question, int questionNumber) {
        this.question = question;
        this.questionNumber = questionNumber;
    }

    public Question(String question, String explanation, int questionNumber, SubCategory questionCategory) {
        this.question = question;
        this.explanation = explanation;
        this.questionNumber = questionNumber;
        this.questionCategory = questionCategory;
    }

    public Question(String question, String explanation, int questionNumber, SubCategory questionCategory, List<Answer> answers) {
        this.question = question;
        this.explanation = explanation;
        this.questionNumber = questionNumber;
        this.questionCategory = questionCategory;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String name) {
        this.question = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public SubCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(SubCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int question_number) {
        this.questionNumber = question_number;
    }

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        this.answers.add(answer);
    }

    public void removeAnswer(Answer answer){
        this.answers.remove(answer);
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question question)) return false;

        return this.uuid.equals(question.uuid);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + uuid +
                ", name='" + question + '\'' +
                ", explanation='" + explanation + '\'' +
                ", question_category=" + questionCategory +
                ", answers=" + answers +
                ", questionnaire=" + questionnaire.getTitle() +
                '}';
    }
}
