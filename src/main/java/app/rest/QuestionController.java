package app.rest;

import app.models.Answer;
import app.models.Question;
import app.models.Result;
import app.repositories.AnswerRepository;
import app.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public ResponseEntity<Question> save(@RequestBody Question question) throws URISyntaxException {
        Question savedQuestion = questionRepository.create(question);
        URI location = new URI("/question/" + savedQuestion.getUuid());
        return ResponseEntity.created(location).body(savedQuestion);
    }

}