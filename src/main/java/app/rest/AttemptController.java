package app.rest;

import app.models.Attempt;
import app.models.Questionnaire;
import app.models.User;
import app.models.view;
import app.repositories.AttemptRepository;
import app.repositories.QuestionnaireRepository;
import app.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/attempt")
public class AttemptController {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    @JsonView(view.Attempt.class)
    public ResponseEntity<Attempt> save(@RequestBody Attempt attempt) throws URISyntaxException {
        Attempt savedAttempt = attemptRepository.create(attempt);
        URI location = new URI("/attempt/" + savedAttempt.getUuid());
        return ResponseEntity.created(location).body(savedAttempt);
    }

    @GetMapping("/personal/{uuid}")
    @JsonView(view.Attempt.class)
    public ResponseEntity<Attempt> findByUuid(@PathVariable String uuid) {
            Attempt attempt = attemptRepository.findByUuid(uuid);
            if (attempt != null) {
                return ResponseEntity.ok(attempt);
            }
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/admin")
    @JsonView(view.Attempt.class)
    public ResponseEntity<Attempt> update(@RequestBody Attempt attempt){
        Attempt updatedAttempt = attemptRepository.update(attempt);
        return ResponseEntity.ok(updatedAttempt);
    }

    @GetMapping("/personal/start/{userUuid}")
    @JsonView(view.Attempt.class)
    public ResponseEntity<Attempt> getCurrentOrNewAttempt(@PathVariable String userUuid){
        Attempt attempt;
        try {
            attempt = attemptRepository.findUncompletedAttemptByUserUuid(userUuid);
        } catch (NoResultException e) {
            Questionnaire currentQuestionnaire = questionnaireRepository.getActive();
            User user = userRepository.findByUuid(userUuid);
            attempt = new Attempt(currentQuestionnaire, user);
            attempt = attemptRepository.create(attempt);
        }
        return ResponseEntity.ok(attempt);
    }

    @PutMapping("/personal/finish/{uuid}")
    public ResponseEntity<Void> finishAttempt(@PathVariable String uuid) {
        Attempt attempt = attemptRepository.findByUuid(uuid);
        attempt.setIs_completed(true);
        attemptRepository.update(attempt);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/personal/progress/{userUuid}")
    public ResponseEntity<String> getProgress(@PathVariable String userUuid) {
        try {
            Attempt attempt = attemptRepository.findUncompletedAttemptByUserUuid(userUuid);
            int questionsFinished = attempt.getResults().size();
            int questionnaireSize = attempt.getQuestionnaire().getQuestions().size();
            String progress = questionsFinished + "/" + questionnaireSize;
            return ResponseEntity.ok(progress);
        } catch (NoResultException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/personal/{userUuid}")
    public ResponseEntity<Void> deleteUserAttempt (@PathVariable String userUuid) {
        try {
            Attempt attempt = attemptRepository.findUncompletedAttemptByUserUuid(userUuid);
            attemptRepository.delete(attempt);
            return ResponseEntity.noContent().build();
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
