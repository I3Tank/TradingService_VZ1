package net.vz1.entity;

import javax.inject.Inject;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Depot")
public class Depot implements Serializable {

    @Id
    @GeneratedValue
    private Integer depotID;

    @OneToOne
    @JoinColumn(name = "Customer", referencedColumnName = "customerID")
    private Customer customer;

    @OneToMany(mappedBy = "depot")
    private List<DepotEntry> depotEntries;

    public Depot(Customer customer){
        this.customer = customer;
    }

    public Depot() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Integer getDepotID() {
        return depotID;
    }

    public void addDepotEntry(DepotEntry depotEntry){
        this.depotEntries.add(depotEntry);
    }

    public List<DepotEntry> getAllDepotEntries(){
        return depotEntries;
    }
}
