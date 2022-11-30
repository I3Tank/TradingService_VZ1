package net.vz1.entity;

import net.vz1.ejb.common.CustomerDTO;

public class CustomerTranslator {
    /** Converts a DTO instance to an entity instance. */
    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) return null;
        return new Customer(customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getAddress(), customerDTO.getPassword());
    }

    /** Converts an entity instance to a DTO instance. */
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;
        return new CustomerDTO(customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getPassword());
    }
}
