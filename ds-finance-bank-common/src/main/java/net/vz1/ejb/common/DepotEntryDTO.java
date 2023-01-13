package net.vz1.ejb.common;

import java.io.Serializable;

public class DepotEntryDTO implements Serializable {
    public int depotID;

    public String symbol;
    public int quantity;

    public DepotEntryDTO(String symbol, int depotID){
        this.symbol = symbol;
        this.depotID = depotID;
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
