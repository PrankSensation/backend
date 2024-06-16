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
public class AnswerRepositoryTest {

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
    AttemptRepository attemptRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SectorRepository sectorRepository;

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
    Category category;

    @BeforeEach
    public void setUp() {
        category = categoryRepository.create(new Category());
        category.addSubCategory(new SubCategory());

        Sector sector = sectorRepository.create(new Sector("test"));

        user1 = new User("test", "test", "test@gmail.com", "test", Roles.USER,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");
        user1.setRandomUuid();
        userRepository.create(user1);

        invalidUser1 = new User("test2", "test2", "test2@gmail.com", "test2", Roles.USER,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");
        invalidUser1.setRandomUuid();
        userRepository.create(invalidUser1);

        answer1 = new Answer("test", 5, "test", 1);
        answerRepository.create(answer1);

        question1 = new Question("test", "test", 1, category.getSubCategories().get(0));
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
        categoryRepository.delete(category);
    }

    @Test
    public void validUserIdShouldReturnResults() {
        List<Object[]> result = answerRepository.getTipsLowestPointsPerCategoryByAttemptId(attempt1.getUuid());
        assertFalse(result.isEmpty());
    }

    @Test
    public void validUnusedUserIdShouldReturnException() {
        List<Object[]> result = answerRepository.getTipsLowestPointsPerCategoryByAttemptId(invalidAttempt1.getUuid());
        assertNull(result);
    }

}
