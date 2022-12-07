package net.vz1.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EmployeeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Employee findEmployeeById(int employeeId) {
        return entityManager.find(Employee.class, employeeId);
    }

    public void persist(Employee employee) {
        entityManager.persist(employee);
    }

    public void delete(Employee employee) {
        entityManager.remove(employee);
    }
}
