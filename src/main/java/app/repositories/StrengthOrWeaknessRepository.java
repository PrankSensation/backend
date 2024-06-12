package app.repositories;

import app.models.StrengthOrWeakness;
import app.models.SubCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StrengthOrWeaknessRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<StrengthOrWeakness> findAll() {
        TypedQuery<StrengthOrWeakness> query = entityManager.createQuery("select strengthOrWeakness from StrengthOrWeakness strengthOrWeakness", StrengthOrWeakness.class);
        return query.getResultList();
    }

    public StrengthOrWeakness create(StrengthOrWeakness strengthOrWeakness) {
        entityManager.persist(strengthOrWeakness);
        return strengthOrWeakness;
    }

    public StrengthOrWeakness findByUuid(String uuid) {
        return entityManager.find(StrengthOrWeakness.class, uuid);
    }

    public StrengthOrWeakness update(StrengthOrWeakness strengthOrWeakness){
        return entityManager.merge(strengthOrWeakness);
    }

    public void deleteByUuid(String uuid) {
        StrengthOrWeakness strengthOrWeakness = this.findByUuid(uuid);
        this.delete(strengthOrWeakness);
    }

    public void delete(StrengthOrWeakness strengthOrWeakness) {
        entityManager.remove(strengthOrWeakness);
    }
}
