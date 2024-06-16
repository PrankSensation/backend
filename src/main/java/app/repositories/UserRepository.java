package app.repositories;

import app.models.Sector;
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

    public Sector findSectorByUserUuid(String userUuid) {
        User user = entityManager.find(User.class, userUuid);
        if (user != null) {
            return user.getSector();
        } else {
            return null;
        }
    }


    public User findByUuidWithSector(String uuid) {
        String jpql = "SELECT u FROM User u JOIN FETCH u.sector WHERE u.uuid = :uuid";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
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

    public Sector getUserSector(String uuid) {
        String jpql = "SELECT u.sector FROM User u WHERE u.uuid = :uuid";
        TypedQuery<Sector> query = entityManager.createQuery(jpql, Sector.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }


}
