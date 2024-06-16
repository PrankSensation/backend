package app.repositories;

import app.models.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ResultRepositoryTest {

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AttemptRepository attemptRepository;

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
        user1 = new User("test", "test", "test@gmail.com", "test", Roles.USER,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");
        userRepository.create(user1);

        invalidUser1 = new User("test2", "test2", "test2@gmail.com", "test2", Roles.USER,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");
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
    public void canFindResultByUuid() throws ChangeSetPersister.NotFoundException {
        Result foundResult = resultRepository.findByUuid(result1.getUuid());
        assertEquals(result1, foundResult);
    }

    @Test
    public void canFindAllResults() {
        List<Result> results = resultRepository.findAll();

        assertEquals(3, results.size());
        assertTrue(results.contains(result1));
        assertTrue(results.contains(result2));
    }

    @Test
    public void validUserIdShouldReturnActiveQuestionnaire() {
        List<Questionnaire> results = resultRepository.getActiveQuestionnaireByUserId(user1.getUuid());

        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void invalidUserIdShouldReturnNull() {
        List<Questionnaire> results = resultRepository.getActiveQuestionnaireByUserId(invalidUser1.getUuid());

        assertNull(results);
    }

    @Test
    public void validUserWithActiveQuestionnaireShouldReturnTrue() {
        boolean results = resultRepository.userMadeActiveQuestionnaire(user1.getUuid());

        assertTrue(results);
    }

    @Test
    public void invalidUserWithoutActiveQuestionnaireShouldReturnFalse() {
        boolean results = resultRepository.userMadeActiveQuestionnaire(invalidUser1.getUuid());

        assertFalse(results);
    }

    @Test
    public void validUserIdShouldReturnLatestQuestionnaire() {
        List<Questionnaire> results = resultRepository.getLatestQuestionnaireFromUserByUserId(user1.getUuid());

        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void invalidUserIdShouldReturnEmptyListForLatestQuestionnaire() {
        List<Questionnaire> results = resultRepository.getLatestQuestionnaireFromUserByUserId(invalidUser1.getUuid());

        assertTrue(results.isEmpty());
    }

    @Test
    public void validUserIdAndQuestionnaireIdShouldReturnLatestQuestionnaireCount() {
        List<Long> results = resultRepository.getLatestQuestionnaireCountFromUserByUserId(user1.getUuid(), questionnaire1.getUuid());

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    public void invalidUserIdShouldReturnZeroCountForLatestQuestionnaire() {
        List<Long> results = resultRepository.getLatestQuestionnaireCountFromUserByUserId(invalidUser1.getUuid(), questionnaire1.getUuid());

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(0L, results.get(0));
    }

    @Test
    public void validUserIdAndQuestionnaireIdShouldReturnAttempt() {
        List<Attempt> results = resultRepository.getAttemptFromLatestQuestionnaireByUserIdAndAttempt(user1.getUuid(), questionnaire1.getUuid(), 1);
        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void invalidUserIdShouldReturnEmptyListForAttempt() {
        List<Attempt> results = resultRepository.getAttemptFromLatestQuestionnaireByUserIdAndAttempt(invalidUser1.getUuid(), questionnaire1.getUuid(), 1);

        assertTrue(results.isEmpty());
    }


    @Test
    public void invalidAttemptIdShouldReturnEmptyListForUserResults() {
        List<Object[]> results = resultRepository.getUserResultsByAttemptId(invalidAttempt1.getUuid());

        assertTrue(results.isEmpty());
    }

    @Test
    public void validQuestionnaireIdShouldReturnLatestAttemptUuids() {
        String[] results = resultRepository.getLatestAttemptUuidsOfSpecificQuestionnaireForAllUsersByQuestionnaireId(questionnaire1.getUuid());

        assertNotNull(results);
        assertTrue(results.length > 0);
    }

    @Test
    public void invalidQuestionnaireIdShouldReturnNullForLatestAttemptUuids() {
        String[] results = resultRepository.getLatestAttemptUuidsOfSpecificQuestionnaireForAllUsersByQuestionnaireId("invalid-uuid");

        assertNull(results);
    }

    @Test
    public void invalidQuestionnaireIdShouldReturnNullForAllResultsByAttemptUuids() {
        List<Object[]> results = resultRepository.getAllResultsByAttemptUuids("invalid-uuid");

        assertNull(results);
    }

}
