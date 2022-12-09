package net.vz1.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CustomerDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Customer findById(Integer customerId) {
        return (Customer) entityManager.find(Customer.class, customerId);
    }

    public void persist(Customer customer) {
        entityManager.persist(customer);
    }

    public void delete(Customer customer) {
        entityManager.remove(customer);
    }
}
