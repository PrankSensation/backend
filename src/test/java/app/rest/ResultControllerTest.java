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

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ResultControllerTest {

    @Autowired
    private ResultController resultController = new ResultController();

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

        question1 = new Question("test",  1);
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
    public void canFindResultByUuid() {
        Result foundResult = resultController.findByUuid(result1.getUuid()).getBody();
        assertEquals(result1, foundResult);
    }

    @Test
    public void canFindAllResults() {
        List<Result> results = resultController.findAll().getBody();

        assert results != null;
        assertEquals(3, results.size());
        assertTrue(results.contains(result1));
        assertTrue(results.contains(result2));
    }

    @Test
    public void validUserWithActiveQuestionnaireShouldReturnTrue() {
        boolean results = Boolean.TRUE.equals(resultController.userMadeActiveQuestionnaire(user1.getUuid()).getBody());

        assertTrue(results);
    }

    @Test
    public void invalidUserWithoutActiveQuestionnaireShouldReturnFalse() {
        boolean results = Boolean.TRUE.equals(resultController.userMadeActiveQuestionnaire(invalidUser1.getUuid()).getBody());

        assertFalse(results);
    }

    @Test
    public void validUserIdShouldReturnLatestQuestionnaire() {
        List<Questionnaire> results = resultController.getLatestQuestionnaireFromUserByUserId(user1.getUuid()).getBody();

        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void invalidUserIdShouldReturnEmptyListForLatestQuestionnaire() {
        List<Questionnaire> results = resultController.getLatestQuestionnaireFromUserByUserId(invalidUser1.getUuid()).getBody();

        assertTrue(results.isEmpty());
    }

    @Test
    public void validUserIdAndQuestionnaireIdShouldReturnLatestQuestionnaireCount() {
        List<Long> results = resultController.getLatestQuestionnaireCountFromUserByUserId(user1.getUuid(), questionnaire1.getUuid()).getBody();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    public void invalidUserIdShouldReturnZeroCountForLatestQuestionnaire() {
        List<Long> results = resultController.getLatestQuestionnaireCountFromUserByUserId(invalidUser1.getUuid(), questionnaire1.getUuid()).getBody();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(0L, results.get(0));
    }

    @Test
    public void validUserIdAndQuestionnaireIdShouldReturnAttempt() {
        List<Attempt> results = resultController.getAttemptFromLatestQuestionnaireByUserIdAndAttempt(user1.getUuid(), questionnaire1.getUuid(), 1).getBody();

        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void invalidUserIdShouldReturnEmptyListForAttempt() {
        List<Attempt> results = resultController.getAttemptFromLatestQuestionnaireByUserIdAndAttempt(invalidUser1.getUuid(), questionnaire1.getUuid(), 1).getBody();

        assert results != null;
        assertTrue(results.isEmpty());
    }

    @Test
    public void invalidAttemptIdShouldReturnEmptyListForUserResults() {
        Map<String, Double> results = resultController.getUserResultsByAttemptId(invalidAttempt1.getUuid()).getBody();

        assert results != null;
        assertTrue(results.isEmpty());
    }

    @Test
    public void invalidQuestionnaireIdShouldReturnNullForAllResultsByAttemptUuids() {
        ResponseEntity<Map<String, Double>> response = resultController.getAllResultsByAttemptUuids("invalid_questionnaire_id");
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

}
