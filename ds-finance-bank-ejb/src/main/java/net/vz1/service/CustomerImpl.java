package net.vz1.service;

import net.vz1.ejb.common.CustomerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="CustomerService")
@PermitAll
public class CustomerImpl implements CustomerInterface {

    private static final Logger log = LoggerFactory.getLogger(CustomerImpl.class);

    /*@Inject
    CustomerDAO customerDAO;
    @Inject
    CustomerTranslator customerTranslator;*/

    public List<String> findAvailableSharesByCompanyName(String companyName) {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        return transactionService.findStockQuotesByCompanyName(companyName);
    }

    public String buyShares(int sharesID, int quantity) {
        return null;
    }

    public String sellShares(int sharesID, int quantity) {
        return null;
    }

    public List<String> getDepotStockQuotes() {
        return null;
    }

    public String searchSharesInDepot(String companyName) {
        return null;
    }

    //-----------------------------Testing
    public List<String> getPublicStockQuotes() {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        return null;
    }
}