package net.vz1.service;

import net.froihofer.dsfinance.ws.trading.*;
import net.vz1.ejb.common.CustomerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless(name="CustomerService")
@PermitAll
public class CustomerImpl implements CustomerInterface {

    private static final Logger log = LoggerFactory.getLogger(CustomerImpl.class);

    /*@Inject
    CustomerDAO customerDAO;
    @Inject
    CustomerTranslator customerTranslator;*/

    public String findAvailableSharesByCompanyName(String companyName) {
        return null;
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
        TradingWebServiceService ts = new TradingWebServiceService();
        TradingWebService tws = ts.getTradingWebServicePort();

        try {
            List<PublicStockQuote> publicStockQuotes = tws.getStockQuoteHistory("");
            List<String> stockNames = new ArrayList<>();

            for ( PublicStockQuote item: publicStockQuotes
            ) {
                stockNames.add(item.getCompanyName());
            }
            return stockNames;

        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }
}