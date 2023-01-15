package net.vz1.ejb.common;

import java.io.Serializable;

public class DepotEntryDTO implements Serializable {
    public int depotID;

    public String symbol;
    public int quantity;

    public DepotEntryDTO(String symbol, int depotID, int quantity){
        this.symbol = symbol;
        this.depotID = depotID;
        this.quantity = quantity;
    }
    public DepotEntryDTO(){

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
}
