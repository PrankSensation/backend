package app.rest;

import app.models.*;
import app.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class QuestionnaireControllerTest {

    @Autowired
    QuestionnaireController questionnaireController;

    @Autowired
    CategoryRepository categoryRepository;

    private Questionnaire questionnaire1;
    private Questionnaire questionnaire2;
    private Category category;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        category = categoryRepository.create(new Category());
        category.addSubCategory(new SubCategory());

        questionnaire1 = new Questionnaire("test questionnaire 1dfadfa", "this is a test questionnaire");

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

        questionnaire1 = questionnaireController.save(questionnaire1).getBody();
        questionnaire2 = questionnaireController.save(new Questionnaire()).getBody();
    }

    @AfterEach
    public void tearDown() {
        questionnaireController.delete(questionnaire1.getUuid());
        questionnaireController.delete(questionnaire2.getUuid());
        categoryRepository.delete(category);
    }

    @Test
    public void saveEnsuresOnlyOneActiveQuestionnaire() throws URISyntaxException {
        Questionnaire newActiveQuestionnaire = new Questionnaire();
        newActiveQuestionnaire.setIsActive(true);

        questionnaireController.save(newActiveQuestionnaire).getBody();

//        Throws error when more than one active
        questionnaireController.getActive();
    }

    @Test
    public void activateEnsuresOnlyOneActiveQuestionnaire() {
        questionnaireController.activate(questionnaire2.getUuid()).getBody();

        Questionnaire oldActiveQuestionnaire = questionnaireController.findByUuid(questionnaire1.getUuid()).getBody();
        assertFalse(oldActiveQuestionnaire.isActive());
    }










}
