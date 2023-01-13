package net.vz1.entity;

import java.io.Serializable;

public class EntryID implements Serializable {
    private String symbol;
    private int depotID;

    public EntryID(String symbol, int depotID){
        this.symbol = symbol;
        this.depotID = depotID;
    }

    public EntryID(){}
}
