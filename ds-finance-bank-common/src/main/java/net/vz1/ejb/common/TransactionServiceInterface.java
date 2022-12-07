package net.vz1.ejb.common;

import java.util.List;

public interface TransactionServiceInterface {
    public List<String> findStockQuotesByCompanyName(String companyName);
}
