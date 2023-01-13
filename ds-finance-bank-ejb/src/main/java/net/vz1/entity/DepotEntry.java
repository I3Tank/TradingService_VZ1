package net.vz1.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DepotEntry")
@IdClass(EntryID.class)
public class DepotEntry implements Serializable {


    @Id
    private int depotID;
    @Id
    private String symbol;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "Depot", referencedColumnName = "depotID")
    private Depot depot;

    public DepotEntry(String symbol, int quantity){
        this.symbol = symbol;
        this.quantity = quantity;
    }
    public DepotEntry(){

    }

    public int getDepotID() {
        return depotID;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setDepotId(int depotID){
        this.depotID = depotID;
    }
}
