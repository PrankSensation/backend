package app.repositories;

import app.models.Answer;
import app.models.Tip;
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

    public List<Tip> getShortTermTips(String attemptUuid) {
        int max_points = this.getMaxPointsInQuestionnaireByAttempt(attemptUuid);

        String queryString = "SELECT NEW " +
                "app.models.Tip (r.question.questionCategory.category.title, r.question.questionCategory.title, r.answer.tip) " +
                "FROM Result r WHERE r.attempt.uuid = :attemptUuid AND SIZE(r.question.answers) > 0 " +
                "ORDER BY r.question.questionCategory.category.weight *  (:max_points - r.answer.points) DESC";
        TypedQuery<Tip> query = entityManager.createQuery(queryString, Tip.class);
        query.setParameter("attemptUuid", attemptUuid);
        query.setParameter("max_points", max_points);

        return query.getResultList();
    }

    private Integer getMaxPointsInQuestionnaireByAttempt(String attemptUuid) {
        String queryString = "SELECT MAX(a.points) FROM Answer a JOIN Result r ON a.uuid = r.answer.uuid WHERE r.attempt.uuid = :attemptUuid";
        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
        query.setParameter("attemptUuid", attemptUuid);

        return query.getSingleResult();
    }

}
