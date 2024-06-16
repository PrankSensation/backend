package app.rest;

import app.models.*;
import app.repositories.ResultRepository;
import app.repositories.TipTextRepository;
import app.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TipTextRepository tipTextRepository;

    @GetMapping()
    public ResponseEntity<List<Result>> findAll(){
        List<Result> results = resultRepository.findAll();
        return ResponseEntity.ok(results);
    }

    @PostMapping()
    public ResponseEntity<Result> save(@RequestBody Result result) throws URISyntaxException {
        Result savedResult = resultRepository.create(result);
        URI location = new URI("/result/" + savedResult.getUuid());
        return ResponseEntity.created(location).body(savedResult);
    }
    @GetMapping("/admin")
    public List<Object[]> findAllAdmin(){
        return resultRepository.findAllAdmin();
    }

    @GetMapping("/personal/{uuid}")
    public ResponseEntity<Result> findByUuid(@PathVariable String uuid) {
        try {
            Result result = resultRepository.findByUuid(uuid);
            return ResponseEntity.ok(result);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping()
    @JsonView(view.Result.class)
    public ResponseEntity<Result> update(@RequestBody Result result){
        Result updatedResult = resultRepository.update(result);
        return ResponseEntity.ok(updatedResult);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid){
        try {
            resultRepository.deleteByUuid(uuid);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user_results/{attemptUuid}")
    public ResponseEntity<Map<String, Double>> getUserResultsByAttemptId(@PathVariable String attemptUuid) {
        List<Object[]> resultList = resultRepository.getUserResultsByAttemptId(attemptUuid);

        Map<String, Double> averagePointsPerCategory = resultList.stream()
                .collect(Collectors.toMap(
                        array -> ((String) array[0]),
                        array -> (Double) array[1]
                ));

        return ResponseEntity.ok(averagePointsPerCategory);
    }

    @GetMapping("/sector_name/personal/{userUuid}/{attemptUuid}")
    public ResponseEntity<List<String>> getSectorNameByUserIdAndAttemptId(@PathVariable String userUuid, @PathVariable String attemptUuid){
        List<String> sectors = resultRepository.getSectorNameByUserIdAndAttemptId(userUuid);
        return ResponseEntity.ok(sectors);
    }

    @GetMapping("/user/questionnaireAmount/personal/{userUuid}")
    public ResponseEntity<Long> countDistinctQuestionnairesByUserUuid(@PathVariable String userUuid) {
        try {
            long results = resultRepository.countDistinctQuestionnairesByUserUuid(userUuid);
            return ResponseEntity.ok(results);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/questionnaireCompleted/personal/{userUuid}")
    public ResponseEntity<List<Questionnaire>> getNonCompletedQuestionnairesByUser(@PathVariable String userUuid) {
        List<Questionnaire> nonCompletedQuestionnaires = resultRepository.getNonCompletedQuestionnairesByUser(userUuid);
        return ResponseEntity.ok(nonCompletedQuestionnaires);
    }

    @GetMapping("/personal/latest_questionnaire/{userUuid}")
    public ResponseEntity<List<Questionnaire>> getLatestQuestionnaireFromUserByUserId(@PathVariable String userUuid){
        List<Questionnaire> sectors = resultRepository.getLatestQuestionnaireFromUserByUserId(userUuid);
        return ResponseEntity.ok(sectors);
    }

    @GetMapping("/personal/made_active_questionnaire/{userUuid}")
    public ResponseEntity<Boolean> userMadeActiveQuestionnaire(@PathVariable String userUuid){
        boolean sectors = resultRepository.userMadeActiveQuestionnaire(userUuid);
        return ResponseEntity.ok(sectors);
    }

    @GetMapping("/latest_questionnaire_attempts_count/{userUuid}/{questionnaireUuid}")
    public ResponseEntity<List<Long>> getLatestQuestionnaireCountFromUserByUserId(@PathVariable String userUuid, @PathVariable String questionnaireUuid){
        List<Long> results = resultRepository.getLatestQuestionnaireCountFromUserByUserId(userUuid, questionnaireUuid);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/attempt/{userUuid}/{questionnaireUuid}/{attempt}")
    public ResponseEntity<List<Attempt>> getAttemptFromLatestQuestionnaireByUserIdAndAttempt(@PathVariable String userUuid, @PathVariable String questionnaireUuid, @PathVariable int attempt){
        List<Attempt> results = resultRepository.getAttemptFromLatestQuestionnaireByUserIdAndAttempt(userUuid, questionnaireUuid, attempt);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/all_latest_attempts_questionnaire/{questionnaireUuid}")
    public ResponseEntity<Map<String, Double>> getAllResultsByAttemptUuids(@PathVariable String questionnaireUuid){
        List<Object[]> results = resultRepository.getAllResultsByAttemptUuids(questionnaireUuid);

        if (results == null || results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Double> averagePointsPerCategory = results.stream()
                .collect(Collectors.toMap(
                        array -> ((String) array[0]),
                        array -> (Double) array[1]
                ));

        return ResponseEntity.ok(averagePointsPerCategory);
    }

    @GetMapping("/sector_results/{attemptUuid}")
    public ResponseEntity<Map<String, Double>> getSectorResultsForLatestAttempts(@PathVariable String attemptUuid){
        List<Object[]> results = resultRepository.getSectorResultsForLatestAttempts(attemptUuid);

        if (results == null || results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<String, Double> averagePointsPerCategory = results.stream()
                .collect(Collectors.toMap(
                        array -> ((String) array[0]),
                        array -> (Double) array[1]
                ));

        return ResponseEntity.ok(averagePointsPerCategory);
    }






}
