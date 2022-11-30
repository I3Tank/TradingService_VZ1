package net.vz1.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomerDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Customer findById(String name) {
        return entityManager.find(Customer.class, name);
    }

    public void persist(Customer customer) {
        entityManager.persist(customer);
    }

    public void delete(Customer customer) {
        entityManager.remove(customer);
    }
}
