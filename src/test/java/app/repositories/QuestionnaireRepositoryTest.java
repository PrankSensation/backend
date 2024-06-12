package app.repositories;

import app.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class QuestionnaireRepositoryTest {

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    CategoryRepository categoryRepository;


    private Questionnaire questionnaire1;
    private Questionnaire questionnaire2;
    private Category category;

    @BeforeEach
    public void setUp(){
        questionnaire1 = new Questionnaire("test questionnaire 1", "this is a test questionnaire");

        category = categoryRepository.create(new Category());
        category.addSubCategory(new SubCategory());

        Question question1 = new Question("what is test question 1?", 1, category.getSubCategories().get(0));
        Question question2 = new Question("what is test question 2?", 2, category.getSubCategories().get(0));

        Answer answer1 = new Answer("Number 1", 1, "Tip 1", 1);
        Answer answer2 = new Answer("Number 2", 2, "Tip 2", 2);
        Answer answer3 = new Answer("Number 3", 1, "Tip 3", 1);

        question1.addAnswer(answer1);
        question1.addAnswer(answer2);

        question2.addAnswer(answer3);

        questionnaire1.addQuestion(question1);

        questionnaire1.setIsActive(true);

        questionnaire1 = questionnaireRepository.create(questionnaire1);

        questionnaire2 = questionnaireRepository.create(new Questionnaire());

    }

    @AfterEach
    public void tearDown() throws NotFoundException {
        questionnaireRepository.deleteByUuid(questionnaire1.getUuid());
        categoryRepository.delete(category);
    }

    @Test
    public void canFindQuestionnaireByUuid() throws NotFoundException {
        Questionnaire foundQuestionnaire = questionnaireRepository.findByUuid(questionnaire1.getUuid());
        assertEquals(questionnaire1, foundQuestionnaire);
    }

    @Test
    public void canFindAllQuestionnaires(){
        List<Questionnaire> questionnaires = questionnaireRepository.findAll();

        assertEquals(2, questionnaires.size());
        assertTrue(questionnaires.contains(questionnaire1));
        assertTrue(questionnaires.contains(questionnaire2));
    }

    @Test
    public void canRetrieveActiveQuestionnaire(){
        Questionnaire activeQuestionnaire = questionnaireRepository.getActive();
        assertEquals(questionnaire1, activeQuestionnaire);
    }

    @Test
    public void canChangeQuestionnaire() {
        Questionnaire questionnaire = questionnaireRepository.getActive();
        String oldTitle = questionnaire.getTitle();
        questionnaire.setTitle("New title");

        Questionnaire updatedQuestionnaire = questionnaireRepository.update(questionnaire);

        assertNotEquals(oldTitle, updatedQuestionnaire.getTitle());
    }

    @Test
    public void findByUuidTrowsErrorWhenNotFound(){
        assertThrows(NotFoundException.class, () -> questionnaireRepository.findByUuid("Fake uuid"));
    }

    @Test
    public void deleteByUuidTrowsErrorWhenNotFound(){
        assertThrows(NotFoundException.class, () -> questionnaireRepository.deleteByUuid("Fake uuid"));
    }

    @Test
    public void getActiveTrowsErrorWhenNotFound() {
        Questionnaire activeQuestionnaire = questionnaireRepository.getActive();
        activeQuestionnaire.setIsActive(false);
        questionnaireRepository.update(activeQuestionnaire);

        assertThrows(NoResultException.class, () -> questionnaireRepository.getActive());
    }

    @Test
    public void testGetQuestionnaireList() {
        List<Questionnaire> result = questionnaireRepository.getQuestionnaireList();

        // Validate the results
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        // Validate the order
        assertThat(result).contains(questionnaire1);
        assertThat(result).contains(questionnaire2);
    }


}
