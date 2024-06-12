package app.rest;

import app.models.Answer;
import app.models.Question;
import app.models.Questionnaire;
import app.models.view;
import app.repositories.QuestionnaireRepository;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.net.URISyntaxException;

@RestController
@RequestMapping()
public class QuestionnaireController {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @GetMapping("/questionnaire")
    @JsonView(view.Questionnaire.class)
    public ResponseEntity<List<Questionnaire>> findAll(){
        List<Questionnaire> questionnaires = questionnaireRepository.findAll();
        return ResponseEntity.ok(questionnaires);
    }

    @PostMapping("/admin/questionnaire")
    @JsonView(view.Questionnaire.class)
    public ResponseEntity<Questionnaire> save(@RequestBody Questionnaire questionnaire) throws URISyntaxException {

        Questionnaire newQuestionnaire = new Questionnaire(questionnaire.getTitle(), questionnaire.getDescription());

//        To be able to save a new questionnaire form a JSON file you need to
        for (Question question : questionnaire.getQuestions()) {
            Question newQuestion = new Question(question.getQuestion(), question.getExplanation(), question.getQuestionNumber(), question.getQuestionCategory());

            for (Answer answer : question.getAnswers()) {
                newQuestion.addAnswer(new Answer(answer.getAnswer(), answer.getPoints(), answer.getTip(), answer.getAnswerNumber()));
            }
            newQuestionnaire.addQuestion(newQuestion);
        }


        Questionnaire savedQuestionnaire = questionnaireRepository.create(newQuestionnaire);
        URI location = new URI("/questionnaire/" + savedQuestionnaire.getUuid());
        return ResponseEntity.created(location).body(savedQuestionnaire);
    }

    @GetMapping("/questionnaire/{uuid}")
    @JsonView(view.Questionnaire.class)
    public ResponseEntity<Questionnaire> findByUuid(@PathVariable String uuid) {
        try {
            Questionnaire questionnaire = questionnaireRepository.findByUuid(uuid);
            return ResponseEntity.ok(questionnaire);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/questionnaire")
    @JsonView(view.Questionnaire.class)
    public ResponseEntity<Questionnaire> update(@RequestBody Questionnaire questionnaire){
        if (!questionnaireRepository.existsByUuid(questionnaire.getUuid())){
            return ResponseEntity.notFound().build();
        }
        else if (questionnaire.isActive()) {
            Questionnaire active_questionnaire = questionnaireRepository.getActive();
            active_questionnaire.setIsActive(false);
            questionnaireRepository.update(active_questionnaire);
        }
        Questionnaire updatedQuestionnaire = questionnaireRepository.update(questionnaire);
        return ResponseEntity.ok(updatedQuestionnaire);
    }

    @DeleteMapping("/admin/questionnaire/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid){
        try {
            questionnaireRepository.deleteByUuid(uuid);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/questionnaire/active")
    public ResponseEntity<Questionnaire> getActive(){
        try{
            Questionnaire active_questionnaire = questionnaireRepository.getActive();
            return ResponseEntity.ok(active_questionnaire);
        } catch (NoResultException noResultException){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/admin/questionnaire/activate/{uuid}")
    public ResponseEntity<Questionnaire> activate(@PathVariable String uuid){
        Questionnaire questionnaire;
        try {
            questionnaire = questionnaireRepository.findByUuid(uuid);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }

        try {
            Questionnaire active_questionnaire = questionnaireRepository.getActive();
            active_questionnaire.setIsActive(false);
            questionnaireRepository.update(active_questionnaire);
            questionnaire.setIsActive(true);
        } catch (NoResultException noResultException){
            questionnaire.setIsActive(true);
        }

       Questionnaire updatedQuestionnaire = questionnaireRepository.update(questionnaire);
        return ResponseEntity.ok(updatedQuestionnaire);
    }

    @PutMapping("/admin/questionnaire/deactivate/{uuid}")
    public ResponseEntity<Void> deactivate(@PathVariable String uuid) {
        try {
            Questionnaire questionnaire = questionnaireRepository.findByUuid(uuid);
            questionnaire.setIsActive(false);
            questionnaireRepository.update(questionnaire);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Questionnaire>> list() {
        try {
            List<Questionnaire> questionnaireList = questionnaireRepository.getQuestionnaireList();
            return ResponseEntity.ok(questionnaireList);
        } catch (NoResultException noResultException) {
            return ResponseEntity.notFound().build();
        }
    }
}
