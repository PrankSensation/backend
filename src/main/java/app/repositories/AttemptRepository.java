package app.repositories;

import app.models.Attempt;
import app.models.view;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AttemptRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Attempt> findAll() {
        TypedQuery<Attempt> query = entityManager.createQuery("select attempt from Attempt attempt", Attempt.class);
        return query.getResultList();
    }

    public Attempt create(Attempt attempt) {
        entityManager.persist(attempt);
        return attempt;
    }

    public Attempt findByUuid(String uuid) {
        return entityManager.find(Attempt.class, uuid);
    }

    public Attempt update(Attempt attempt){
        return entityManager.merge(attempt);
    }

    public void deleteByUuid(String uuid) {
        Attempt attempt = this.findByUuid(uuid);
        entityManager.remove(attempt);
    }

    public void delete(Attempt attempt) {
        entityManager.remove(attempt);
    }

    public Attempt findUncompletedAttemptByUserUuid(String userUuid) throws NoResultException {
        TypedQuery<Attempt> query = entityManager.createQuery("select attempt from Attempt attempt where attempt.user.uuid = :userUuid and attempt.is_completed = FALSE ", Attempt.class);
        query.setParameter("userUuid", userUuid);
        return query.getSingleResult();
    }

    public List<Attempt> findCompletedAttemptsByUserUuid(String userUuid){
        TypedQuery<Attempt> query = entityManager.createQuery("select attempt from Attempt attempt where attempt.user.uuid = :userUuid and attempt.is_completed = TRUE", Attempt.class);
        query.setParameter("userUuid", userUuid);
        return query.getResultList();
    }
}
