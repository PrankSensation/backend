package app.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import app.models.WebsiteImage;

import java.util.List;

@Repository
@Transactional
public class WebsiteImageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WebsiteImage save(WebsiteImage webisteImage) {
        entityManager.persist(webisteImage);
        return webisteImage;
    }

    public WebsiteImage delete(WebsiteImage webisteImage) {
        entityManager.remove(webisteImage);
        return webisteImage;
    }

    public List<WebsiteImage> findAll() {
        return entityManager.createQuery("from WebsiteImage", WebsiteImage.class).getResultList();
    }

    public WebsiteImage update(WebsiteImage webisteImage) {
        return entityManager.merge(webisteImage);
    }
    public WebsiteImage findByUuid(int uuid) {
        return entityManager.find(WebsiteImage.class, uuid);
    }

}
