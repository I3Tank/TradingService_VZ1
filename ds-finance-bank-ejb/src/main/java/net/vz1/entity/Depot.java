package net.vz1.entity;

import net.vz1.ejb.common.StockQuoteDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "Depot")
public class Depot implements Serializable {
    private Integer ownerID;
    @Id
    @GeneratedValue
    private Integer depotID;

    //private Map<shares, quantity>();

    //TODO
    //private Map<StockQuoteDTO, Integer> depotEntries;
    private Integer stockID;
    private Integer quantity;

    public Depot(Integer ownerID, Integer depotID, Map<StockQuoteDTO, Integer> depotEntries) {
        this.ownerID = ownerID;
        this.depotID = depotID;
        //this.depotEntries = depotEntries;
    }

    public Depot() {

    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public Integer getDepotID() {
        return depotID;
    }

    public Map<StockQuoteDTO, Integer> getDepotEntries() {
        return null;
    }


}
