package app.repositories;

import app.models.WebsiteInformation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class WebsiteInformationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WebsiteInformation findByUuid(String uuid) {
        return entityManager.find(WebsiteInformation.class, uuid);
    }

    public List<WebsiteInformation> findAll() {
        return entityManager.createQuery("select w from WebsiteInformation w", WebsiteInformation.class).getResultList();
    }

    public WebsiteInformation save(WebsiteInformation websiteInformation) {
        entityManager.persist(websiteInformation);
        return websiteInformation;
    }

    public WebsiteInformation update(WebsiteInformation websiteInformation) {
        entityManager.merge(websiteInformation);
        return websiteInformation;
    }

    public void delete(WebsiteInformation websiteInformation) {
        entityManager.remove(websiteInformation);
    }

    public WebsiteInformation getWebsiteInformationByPageName(String pageName) {
        return entityManager.createQuery("select w from WebsiteInformation w where w.pageName = :pageName", WebsiteInformation.class).setParameter("pageName", pageName).getSingleResult();
    }

}
