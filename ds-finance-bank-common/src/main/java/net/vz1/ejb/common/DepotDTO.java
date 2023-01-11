package net.vz1.ejb.common;

import java.util.HashMap;
import java.util.Map;

public class DepotDTO {
    private Integer ownerID;
    private Integer depotID;

    //private Map<shares, quantity>();
    private Map<StockQuoteDTO, Integer> depotEntries;

    public DepotDTO(Integer ownerID, Integer depotID, Map<StockQuoteDTO, Integer> depotEntries) {
        this.ownerID = ownerID;
        this.depotID = depotID;
        this.depotEntries = new HashMap<StockQuoteDTO, Integer>();
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public Integer getDepotID() {
        return depotID;
    }

    public Map<StockQuoteDTO, Integer> getDepotEntries() {
        return depotEntries;
    }
}
