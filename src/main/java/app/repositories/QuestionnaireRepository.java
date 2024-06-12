package app.repositories;

import app.models.Questionnaire;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class QuestionnaireRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Questionnaire> findAll() {
        TypedQuery<Questionnaire> query = entityManager.createQuery("select u from Questionnaire u", Questionnaire.class);
        return query.getResultList();
    }

    public Questionnaire create(Questionnaire questionnaire) {
        entityManager.persist(questionnaire);
        return questionnaire;
    }

    public Questionnaire findByUuid(String uuid) throws NotFoundException {
        Questionnaire questionnaire = entityManager.find(Questionnaire.class, uuid);
        if (questionnaire == null) throw new NotFoundException();
        return questionnaire;
    }

    public Questionnaire update(Questionnaire questionnaire) {
        return entityManager.merge(questionnaire);
    }

    public void deleteByUuid(String uuid) throws NotFoundException {
        Questionnaire optionalQuestionnaire = this.findByUuid(uuid);
        entityManager.remove(optionalQuestionnaire);
    }

    public boolean existsByUuid(String uuid) {
        try {
            this.findByUuid(uuid);
        } catch (NotFoundException notFoundException) {
            return false;
        }
        return true;
    }

    public Questionnaire getActive() throws NoResultException {
        TypedQuery<Questionnaire> query = entityManager.createQuery(
                "SELECT questionnaire FROM Questionnaire questionnaire WHERE questionnaire.isActive = true",
                Questionnaire.class
        );

        return query.getSingleResult();

    }

    public List<Questionnaire> getQuestionnaireList() {
        TypedQuery<Questionnaire> query = entityManager.createQuery(
                "SELECT q.uuid, q.date, q.title, q.isActive FROM Questionnaire q ORDER BY q.date DESC",
                Questionnaire.class
        );
        return query.getResultList();
    }
}
