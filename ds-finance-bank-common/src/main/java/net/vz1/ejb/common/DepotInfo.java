package net.vz1.ejb.common;

import java.io.Serializable;
import java.util.List;

public class DepotInfo implements Serializable {

    public List<StockQuoteDTO> stockQuoteDTOs;
    public List<DepotEntryDTO> depotEntryDTOs;

    public DepotInfo(List<StockQuoteDTO> stockQuoteDTOs, List<DepotEntryDTO> depotEntryDTOs){
        this.stockQuoteDTOs = stockQuoteDTOs;
        this.depotEntryDTOs = depotEntryDTOs;
    }


    public List<StockQuoteDTO> getStockQuoteDTOs() {
        return stockQuoteDTOs;
    }

    public void setStockQuoteDTOs(List<StockQuoteDTO> stockQuoteDTOs) {
        this.stockQuoteDTOs = stockQuoteDTOs;
    }

    public List<DepotEntryDTO> getDepotEntryDTOs() {
        return depotEntryDTOs;
    }

    public void setDepotEntryDTOs(List<DepotEntryDTO> depotEntryDTOs) {
        this.depotEntryDTOs = depotEntryDTOs;
    }
}
