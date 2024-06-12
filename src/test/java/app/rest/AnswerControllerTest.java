package app.rest;

import app.models.*;
import app.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AnswerControllerTest {

    @Autowired
    AttemptRepository attemptRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AnswerController answerController;

    Result result1;
    Result result2;
    Result result3;
    User user1;
    User invalidUser1;
    Questionnaire questionnaire1;
    Question question1;
    Answer answer1;
    Attempt attempt1;
    Attempt invalidAttempt1;

    @BeforeEach
    public void setUp() {
        user1 = new User("test", "test", "test@gmail.com", "test", Roles.USER,"test","test","test");
        userRepository.create(user1);

        invalidUser1 = new User("test2", "test2", "test2@gmail.com", "test2", Roles.USER,"test","test","test");
        userRepository.create(invalidUser1);

        answer1 = new Answer("test", 5, "test", 1);
        answerRepository.create(answer1);

        question1 = new Question("test", 1);
        question1.addAnswer(answer1);
        questionRepository.create(question1);

        questionnaire1 = new Questionnaire("test", "test");
        questionnaire1.addQuestion(question1);
        questionnaire1.setIsActive(true);
        questionnaireRepository.create(questionnaire1);

        attempt1 = new Attempt(questionnaire1, user1);
        attempt1.setIs_completed(true);
        attempt1 = attemptRepository.create(attempt1);

        invalidAttempt1 = new Attempt();
        invalidAttempt1 = attemptRepository.create(invalidAttempt1);

        result1 = new Result(answer1, null, attempt1);
        result1.setQuestion(question1);
        resultRepository.create(result1);

        result2 = new Result("test result 2");
        result2 = resultRepository.create(result2);

        result3 = new Result(answer1, null, attempt1);
        result3.setQuestion(question1);
        resultRepository.create(result3);
    }

    @AfterEach
    public void tearDown() throws ChangeSetPersister.NotFoundException {
        resultRepository.deleteByUuid(result1.getUuid());
        resultRepository.deleteByUuid(result2.getUuid());
        resultRepository.deleteByUuid(result3.getUuid());
        userRepository.delete(user1);
        userRepository.delete(invalidUser1);
    }

    @Test
    public void validUnusedUserIdShouldReturnNoContent() {
        ResponseEntity<Map<QuestionCategory, String>> response = answerController.getTipsLowestPointsPerCategoryByAttemptId(invalidAttempt1.getUuid());
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

}
