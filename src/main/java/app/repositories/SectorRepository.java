package app.repositories;

import app.models.Sector;
import app.models.StrengthOrWeakness;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SectorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Sector> findAll() {
        TypedQuery<Sector> query = entityManager.createQuery(
                "select new app.models.Sector( s.title, s.best_organisation, s.person_of_intrest, s.researcher, s.link, s.uuid) from Sector s", Sector.class);
        return query.getResultList();
    }
    public Sector create(Sector sector) {
        entityManager.persist(sector);
        return sector;
    }

    public Sector findByUuid(String uuid) {
        return entityManager.find(Sector.class, uuid);
    }

    public Sector update(Sector sector){
        return entityManager.merge(sector);
    }

    public void deleteByUuid(String uuid) {
        Sector sector = this.findByUuid(uuid);
        this.delete(sector);
    }

    public void delete(Sector sector) {
        entityManager.remove(sector);
    }
}
