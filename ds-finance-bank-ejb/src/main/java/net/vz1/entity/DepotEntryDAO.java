package net.vz1.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class DepotEntryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<DepotEntry> findDepotEntriesByDepotId(int depotId) {
        return entityManager.createQuery(
                        "SELECT e FROM DepotEntry e where e.depotID = :depotId", DepotEntry.class)
                .setParameter("depotId", depotId).getResultList();
    }

    public void persist(DepotEntry depotEntry) {
        entityManager.persist(depotEntry);
    }

    public void delete(DepotEntry depotEntry) {
        entityManager.remove(depotEntry);
    }
}
