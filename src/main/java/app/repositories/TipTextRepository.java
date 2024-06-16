package app.repositories;

import app.models.Sector;
import app.models.TipText;
import app.models.TipTextPart;
import app.models.TipType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class TipTextRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TipText> getTipTextForTipType(TipType tipType){
        String jpql = "SELECT t FROM TipText t WHERE t.tip_type = :tipType ORDER BY t.tip_text_part ASC";
        TypedQuery<TipText> query = entityManager.createQuery(jpql, TipText.class);
        query.setParameter("tipType", tipType);
        return query.getResultList();
    }

    public TipText updateTipText(TipText tipText){
        return entityManager.merge(tipText);
    }

}
