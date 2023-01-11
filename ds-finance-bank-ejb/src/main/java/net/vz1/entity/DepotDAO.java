package net.vz1.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DepotDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Depot findDepotById(int depotId) {
        return entityManager.find(Depot.class, depotId);
    }

    public void persist(Depot depot) {
        entityManager.persist(depot);
    }

    public void delete(Depot depot) {
        entityManager.remove(depot);
    }
}
