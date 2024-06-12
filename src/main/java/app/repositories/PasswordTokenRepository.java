package app.repositories;

import app.models.PasswordResetToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class PasswordTokenRepository {

    @PersistenceContext
    private EntityManager em;


    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        em.persist(passwordResetToken);
        return passwordResetToken;
    }


    public ResponseEntity<PasswordResetToken> deleteByUuid(String uuid) {
        PasswordResetToken passwordResetToken = em.find(PasswordResetToken.class, uuid);
        em.remove(passwordResetToken);
        return ResponseEntity.ok(passwordResetToken);
    }

    public PasswordResetToken findByUuid(String uuid) {
        return em.find(PasswordResetToken.class, uuid);
    }

}
