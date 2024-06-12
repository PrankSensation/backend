package app.repositories;

import app.models.Attempt;
import app.models.Questionnaire;
import app.models.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class ResultRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Result> findAll() {
        TypedQuery<Result> query = entityManager.createQuery("select result from Result result", Result.class);
        return query.getResultList();
    }

    public Result create(Result result) {
        entityManager.persist(result);
        return result;
    }

    public Result findByUuid(String uuid) throws NotFoundException {
        Result result = entityManager.find(Result.class, uuid);
        if (result == null) throw new NotFoundException();
        return result;
    }

    public Result update(Result result){
        return entityManager.merge(result);
    }

    public void deleteByUuid(String uuid) throws NotFoundException {
        Result optionalResult = this.findByUuid(uuid);
        entityManager.remove(optionalResult);
    }

    public boolean existsByUuid(String uuid){
        try {
            this.findByUuid(uuid);
        } catch (NotFoundException notFoundException) {
            return false;
        }
        return true;
    }

    public List<Questionnaire> getActiveQuestionnaireByUserId(String userUuid){
        String queryString = "SELECT q " +
                "FROM Attempt a " +
                "JOIN Questionnaire q ON a.questionnaire.uuid = q.uuid " +
                "WHERE a.user.uuid = :userUuid " +
                "AND q.isActive = TRUE";

        TypedQuery<Questionnaire> query = entityManager.createQuery(queryString, Questionnaire.class);
        query.setParameter("userUuid", userUuid);

        List<Questionnaire> resultsList = query.getResultList();

        if(resultsList.isEmpty()){
            return null;
        }

        return resultsList;
    }

    public boolean userMadeActiveQuestionnaire(String userUuid) {
        List<Questionnaire> activeQuestionnaire = getActiveQuestionnaireByUserId(userUuid);
        return activeQuestionnaire != null;
    }

    public List<Questionnaire> getLatestQuestionnaireFromUserByUserId(String userUuid) {
        List<Questionnaire> activeQuestionnaire = getActiveQuestionnaireByUserId(userUuid);

        String queryString = "SELECT qn " +
                "FROM Result r " +
                "JOIN r.question q " +
                "JOIN q.questionnaire qn " +
                "WHERE r.attempt.user.uuid = :userUuid " +
                "ORDER BY qn.date DESC";

        TypedQuery<Questionnaire> query = entityManager.createQuery(queryString, Questionnaire.class);
        query.setParameter("userUuid", userUuid);
        query.setMaxResults(1);

        List<Questionnaire> resultsList = query.getResultList();

        if (resultsList.isEmpty()){
            return Collections.emptyList();
        }

        if(activeQuestionnaire == null){
            return resultsList;
        }

        if (activeQuestionnaire.get(0).getDate().before(resultsList.get(0).getDate())) {
            return activeQuestionnaire;
        }

        return resultsList;
    }

    public List<Long> getLatestQuestionnaireCountFromUserByUserId(String userUuid, String questionnaireUuid){
        String queryString = "SELECT COUNT(a) " +
                "FROM Attempt a " +
                "WHERE a.user.uuid = :userUuid " +
                "AND a.questionnaire.uuid = :questionnaireUuid " +
                "AND a.is_completed = true";

        TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        query.setParameter("userUuid", userUuid);
        query.setParameter("questionnaireUuid", questionnaireUuid);

        List<Long> version = query.getResultList();

        if (version.isEmpty()) {
            return List.of(0L);
        }

        return version;
    }

    public List<Attempt> getAttemptFromLatestQuestionnaireByUserIdAndAttempt(String userUuid, String questionnaireUuid, int attempt){
        String queryString = "SELECT a.uuid " +
                "FROM Attempt a " +
                "WHERE a.user.uuid = :userUuid " +
                "AND a.questionnaire.uuid = :questionnaireUuid " +
                "AND a.is_completed = true " +
                "ORDER BY a.date ASC";

        TypedQuery<Attempt> query = entityManager.createQuery(queryString, Attempt.class);
        query.setParameter("userUuid", userUuid);
        query.setParameter("questionnaireUuid", questionnaireUuid);
        query.setFirstResult(attempt - 1);
        query.setMaxResults(1);

        List<Attempt> resultsList = query.getResultList();

        if (resultsList.isEmpty()) {
            return Collections.emptyList();
        }

        return resultsList;
    }

    //OwnAverages
    public List<Object[]> getUserResultsByAttemptId(String attemptUuid){
        String queryString = "SELECT q.questionCategory, ROUND(AVG(a.points), 0) " +
                "FROM Result r " +
                "JOIN Answer a ON r.answer.uuid = a.uuid " +
                "JOIN Question q ON a.question.uuid = q.uuid " +
                "WHERE r.attempt.uuid = :attemptUuid " +
                "AND r.result IS NULL " +
                "AND a.points > 0 " +
                "GROUP BY q.questionCategory";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setParameter("attemptUuid", attemptUuid);

        List<Object[]> resultsList = query.getResultList();

        if (resultsList.isEmpty()) {
            return Collections.emptyList();
        }

        return resultsList;
    }

    public String[] getLatestAttemptUuidsOfSpecificQuestionnaireForAllUsersByQuestionnaireId(String questionnaireUuid) {
        String queryString = "SELECT a.uuid " +
                "FROM Attempt a " +
                "WHERE a.questionnaire.uuid = :questionnaireUuid " +
                "AND a.date = (" +
                "    SELECT MAX(attempt.date) " +
                "    FROM Attempt attempt " +
                "    WHERE attempt.questionnaire.uuid = :questionnaireUuid " +
                "    AND attempt.user.uuid = a.user.uuid " +
                "    AND attempt.is_completed = TRUE " +
                ")";

        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        query.setParameter("questionnaireUuid", questionnaireUuid);

        List<String> attemptUuids = query.getResultList();

        if (attemptUuids.isEmpty()) {
            return null;
        }

        return attemptUuids.toArray(new String[0]);
    }


    public List<Object[]> getAllResultsByAttemptUuids(String questionnaireUuid){
        String[] attemptUuids = getLatestAttemptUuidsOfSpecificQuestionnaireForAllUsersByQuestionnaireId(questionnaireUuid);

        if (attemptUuids == null){
            return null;
        }

        String queryString = "SELECT q.questionCategory, ROUND(AVG(a.points), 0) " +
                "FROM Result r " +
                "JOIN Answer a ON r.answer.uuid = a.uuid " +
                "JOIN Question q ON a.question.uuid = q.uuid " +
                "WHERE r.result IS NULL " +
                "AND r.attempt.uuid IN :attemptUuids " +
                "AND a.points > 0 " +
                "GROUP BY q.questionCategory";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setParameter("attemptUuids", Arrays.asList(attemptUuids));

        List<Object[]> result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }


    public List<String> getSectorNameByUserIdAndAttemptId(String userUuid, String attemptUuid){
        String queryString = "SELECT a.answer " +
                "FROM Result r " +
                "JOIN Question q ON r.question.uuid = q.uuid " +
                "JOIN Answer a ON r.answer.uuid = a.uuid " +
                "WHERE r.attempt.user.uuid = :userUuid " +
                "AND r.attempt.uuid = :attemptUuid " +
                "AND q.questionCategory = 'Sector'";

        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        query.setParameter("userUuid", userUuid);
        query.setParameter("attemptUuid", attemptUuid);

        List<String> answerValues = query.getResultList();

        if (answerValues.isEmpty()) {
            return null;
        }

        return answerValues;
    }

    public List<String> getSectorAnswerIdByAttemptUuid(String attemptUuid) {
        String queryString = "SELECT a.uuid " +
                "FROM Result r " +
                "JOIN Answer a ON r.answer.uuid = a.uuid " +
                "JOIN Question q ON a.question.uuid = q.uuid " +
                "WHERE r.attempt.uuid = :attemptUuid " +
                "AND q.questionCategory = 'Sector'";

        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        query.setParameter("attemptUuid", attemptUuid);

        List<String> answerUuids = query.getResultList();

        if(answerUuids.isEmpty()){
            return null;
        }

        return answerUuids;
    }

    public List<Object[]> getLatestSectorAttemptsByAnswerAndQuestionnaireId(String attemptUuid, String questionnaireUuid) {
        List<String> answerUuid = getSectorAnswerIdByAttemptUuid(attemptUuid);

        if (answerUuid == null) {
            return null;
        }

        String queryString = "SELECT a.uuid AS attempt_uuid " +
                "FROM ( " +
                "    SELECT a.user.uuid AS uuid, MAX(a.date) AS latest_date " + // Added alias "uuid" here
                "    FROM Attempt a " +
                "    JOIN Result r ON a.uuid = r.attempt.uuid " +
                "    WHERE r.answer.uuid = :answerUuid " +
                "    AND a.questionnaire.uuid = :questionnaireUuid " +
                "    GROUP BY a.user.uuid " +
                ") la " +
                "JOIN Attempt a ON la.uuid = a.user.uuid AND la.latest_date = a.date " +
                "WHERE a.is_completed = TRUE " +
                "AND a.questionnaire.uuid = :questionnaireUuid";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setParameter("answerUuid", answerUuid.get(0));
        query.setParameter("questionnaireUuid", questionnaireUuid);

        List<Object[]> result = query.getResultList();

        if(result.isEmpty()) {
            return null;
        }

        return result;
    }


    public List<Object[]> getSectorResultsForLatestAttempts(String attemptUuid, String questionnaireUuid){
        List<Object[]> attemptUuids = getLatestSectorAttemptsByAnswerAndQuestionnaireId(attemptUuid, questionnaireUuid);
        System.out.println("attemptUuids" + Arrays.toString(attemptUuids.get(0)));
        if (attemptUuids == null) {
            return null;
        }

        List<String> attemptUuidsList = attemptUuids.stream()
                .map(attempt -> (String) attempt[0])
                .toList();

        String queryString = "SELECT q.questionCategory, ROUND(AVG(a.points), 0) " +
                "FROM Result r " +
                "JOIN Answer a ON r.answer.uuid = a.uuid " +
                "JOIN Question q ON a.question.uuid = q.uuid " +
                "WHERE r.result IS NULL " +
                "AND r.attempt.uuid IN :attemptUuids " +
                "AND a.points > 0 " +
                "GROUP BY q.questionCategory";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        query.setParameter("attemptUuids", attemptUuidsList);
        List<Object[]> result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    public long countDistinctQuestionnairesByUserUuid(String userUuid) {
        String jpql = "SELECT COUNT(*) " +
                "FROM Attempt a " +
                "JOIN Questionnaire q ON a.questionnaire.uuid = q.uuid " +
                "WHERE a.user.uuid = :userUuid " +
                "AND a.is_completed = TRUE";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("userUuid", userUuid);

        Long count = query.getSingleResult();

        if (count == null) {
            return 0;
        }

        return count;
    }

    public List<Questionnaire> getNonCompletedQuestionnairesByUser(String userUuid) {
        String jpql = "SELECT q " +
                "FROM Attempt a " +
                "JOIN Questionnaire q ON a.questionnaire.uuid = q.uuid " +
                "WHERE a.user.uuid = :userUuid " +
                "AND a.is_completed = FALSE";

        TypedQuery<Questionnaire> query = entityManager.createQuery(jpql, Questionnaire.class);
        query.setParameter("userUuid", userUuid);

        List<Questionnaire> results = query.getResultList();

        if(results.isEmpty()){
            return null;
        }

        return results;
    }

}
