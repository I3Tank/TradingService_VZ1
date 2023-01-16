package net.vz1.ejb.common;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionServiceInterface {
    public List<String> findStockQuotesByCompanyName(String companyName);

    public BigDecimal buyShares(String symbol, int quantity);

    public BigDecimal sellShares(String symbol, int quantity);

    public List<StockQuoteDTO> findStockQuotesBySymbol(List<String> symbol);

    public List<StockQuoteDTO> checkInvestableVolume();
}
