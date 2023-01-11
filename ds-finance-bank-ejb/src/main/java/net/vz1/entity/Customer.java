package net.vz1.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    private Integer customerID;
    private String firstName = "";
    private String lastName = "";
    private String password = "";
    private String address = "";


    public Customer(){}

    //TODO return customer ID?
    public Customer(String firstName, String lastName, String address, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.password = password;
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
