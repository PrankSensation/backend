package app.repositories;

import app.models.Category;
import app.models.SubCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SubCategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SubCategory> findAll() {
        TypedQuery<SubCategory> query = entityManager.createQuery("select subCategory from SubCategory subCategory", SubCategory.class);
        return query.getResultList();
    }

    public SubCategory create(SubCategory subCategory) {
        entityManager.persist(subCategory);
        return subCategory;
    }

    public SubCategory findByUuid(String uuid) {
        return entityManager.find(SubCategory.class, uuid);
    }

    public SubCategory update(SubCategory subCategory){
        return entityManager.merge(subCategory);
    }

    public void deleteByUuid(String uuid) {
        SubCategory subCategory = this.findByUuid(uuid);
        this.delete(subCategory);
    }

    public void delete(SubCategory subCategory) {
        entityManager.remove(subCategory);
    }

    public List<SubCategory> findAllSorted() {
        TypedQuery<SubCategory> query = entityManager.createQuery("SELECT sc.uuid, sc.title, c FROM SubCategory sc JOIN sc.category c ORDER BY c.title", SubCategory.class);

        return query.getResultList();
    }
}
