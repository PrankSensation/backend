package app.repositories;

import app.models.User;
import app.models.view;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Repository
@Transactional
@JsonView(view.User.class)
public class UserRepository {


    @PersistenceContext
    private EntityManager entityManager;


    public List<User> findAll() {
        String jpql = "SELECT u FROM User u ORDER BY " +
                "CASE WHEN u.firstName IS NULL OR u.lastName IS NULL OR u.email IS NULL THEN 1 ELSE 0 END, " +
                "u.firstName, u.lastName, u.email";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }

    public User create(@RequestBody User user) {
        entityManager.persist(user);
        return user;
    }

    public User delete(@RequestBody User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
        return user;
    }

    public User findByUuid(String uuid) {
        return entityManager.find(User.class, uuid);
    }

    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where email = :email", User.class);
        query.setParameter("email", email);
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public User update(User user) {
        entityManager.merge(user);
        return user;
    }


}
