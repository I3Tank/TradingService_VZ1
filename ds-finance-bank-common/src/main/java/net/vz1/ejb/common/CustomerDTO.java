package net.vz1.ejb.common;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private Integer customerID;
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    //private Map<shares, quantity>();

    public CustomerDTO() {
    }

    public CustomerDTO(String firstName, String lastName, String password, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
    }

    //Getter
    public Integer getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    //Setter
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
