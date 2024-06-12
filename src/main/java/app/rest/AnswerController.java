package app.rest;

import app.models.Answer;
import app.models.QuestionCategory;
import app.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping
    public ResponseEntity<Answer> save(@RequestBody Answer answer) throws URISyntaxException {
        Answer savedAnswer = answerRepository.create(answer);
        URI location = new URI("/answer/" + savedAnswer.getUuid());
        return ResponseEntity.created(location).body(savedAnswer);
    }

    @GetMapping("/tips/personal/{attemptUuid}")
    public ResponseEntity<Map<QuestionCategory, String>> getTipsLowestPointsPerCategoryByAttemptId(@PathVariable String attemptUuid) {
        List<Object[]> resultList = answerRepository.getTipsLowestPointsPerCategoryByAttemptId(attemptUuid);

        if (resultList == null || resultList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Map<QuestionCategory, String> averagePointsPerCategory = resultList.stream()
                .collect(Collectors.toMap(
                        array -> ((QuestionCategory) array[0]),
                        array -> (String) array[1]
                ));

        return ResponseEntity.ok(averagePointsPerCategory);
    }

}
