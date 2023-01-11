package net.vz1.entity;

import net.vz1.ejb.common.CustomerDTO;
import net.vz1.ejb.common.CustomerInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomerDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Customer findById(Integer customerId) {
        return (Customer) entityManager.find(Customer.class, customerId);
    }
    public List<Customer> findByFullName(String[] fullName){
        return entityManager.createQuery(
                "SELECT c FROM Customer c where c.firstName like :firstName and c.lastName like :lastName order by c.lastName, c.firstName", Customer.class)
                .setParameter("firstName", "%" + fullName[0] + "%").setParameter("lastName", "%" + fullName[1] + "%").getResultList();
    }
    public void persist(Customer customer) {
        entityManager.persist(customer);
    }

    public void delete(Customer customer) {
        entityManager.remove(customer);
    }
}
