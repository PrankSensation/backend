package app.repositories;

import app.models.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class QuestionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Question create(Question question) {
        entityManager.persist(question);
        return question;
    }


}
