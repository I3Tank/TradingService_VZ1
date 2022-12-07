package net.vz1.entity;

import net.vz1.ejb.common.EmployeeDTO;

public class EmployeeTranslator {

/** Converts a DTO instance to an entity instance. **/

    public Employee toEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) return null;
        return new Employee(employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getPassword());
    }


/** Converts an entity instance to a DTO instance. **/

    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;
        return new EmployeeDTO(employee.getFirstName(), employee.getLastName(), employee.getPassword());
    }
}

