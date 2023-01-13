package net.vz1.ejb.common;

import java.io.Serializable;

public class DepotDTO implements Serializable {
    private Integer depotID;

    private Integer customerID;

    public DepotDTO(Integer depotID, Integer customerID) {
        this.depotID = depotID;
        this.customerID = customerID;
    }

    public Integer getDepotID() {
        return depotID;
    }

    public Integer getCustomerID(){
        return customerID;
    }
}
