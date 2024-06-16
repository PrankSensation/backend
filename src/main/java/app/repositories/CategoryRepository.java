package app.repositories;

import app.models.Attempt;
import app.models.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createQuery("select category from Category category", Category.class);
        return query.getResultList();
    }

    public Category create(Category category) {
        entityManager.persist(category);
        return category;
    }

    public Category findByUuid(String uuid) {
        return entityManager.find(Category.class, uuid);
    }

    public Category update(Category category){
        return entityManager.merge(category);
    }

    public void deleteByUuid(String uuid) {
        Category category = this.findByUuid(uuid);
        this.delete(category);
    }

    public void delete(Category category) {
        entityManager.remove(category);
    }

    public Category get_category_by_title(String title){
        TypedQuery<Category> query = entityManager.createQuery("SELECT category " +
                "FROM Category category WHERE category.title = :title", Category.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }
}
