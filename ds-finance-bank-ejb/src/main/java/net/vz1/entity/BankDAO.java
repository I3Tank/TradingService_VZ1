package net.vz1.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BankDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Bank getBank(){
        return entityManager.createQuery("SELECT b FROM Bank b where b.id = :id", Bank.class)
                .setParameter("id", 1).getSingleResult();
    }

    public void persist(Bank bank){
        entityManager.persist(bank);
    }

    public void update(Bank bank){
        entityManager.merge(bank);
    }
}
