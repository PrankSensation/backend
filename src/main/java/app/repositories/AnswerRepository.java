package app.repositories;

import app.models.Answer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AnswerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Answer create(Answer answer) {
        entityManager.persist(answer);
        return answer;
    }

    public List<Object[]> getTipsLowestPointsPerCategoryByAttemptId(String attemptUuid) {
        String queryString = "SELECT q.questionCategory, MIN(a.tip), MIN(a.points) " +
                "FROM Result r " +
                "JOIN Answer a ON r.answer.uuid = a.uuid " +
                "JOIN Question q ON r.question.uuid = q.uuid " +
                "WHERE r.attempt.uuid = :attemptUuid " +
                "AND a.points > 0 " +
                "GROUP BY q.questionCategory.uuid";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setParameter("attemptUuid", attemptUuid);

        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        return resultList;
    }

}
