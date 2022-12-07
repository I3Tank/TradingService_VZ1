package net.vz1.ejb.common;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {


    private Integer EmployeeID;
    private String firstName;
    private String lastName;
    private String password;

    //private Map<shares, quantity>();

    public EmployeeDTO() {
    }

    public EmployeeDTO(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    //Getter
    public Integer getEmployeeID() {
        return EmployeeID;
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


    //Setter
    public void setEmployeeID(Integer EmployeeID) {
        this.EmployeeID = EmployeeID;
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


}


