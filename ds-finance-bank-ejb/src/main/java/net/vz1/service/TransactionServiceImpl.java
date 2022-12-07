package net.vz1.service;

import net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import net.vz1.ejb.common.TransactionServiceInterface;

import javax.xml.ws.BindingProvider;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionServiceInterface {

    private TradingWebService createConnection(){
        //Create a TradingWebService
        TradingWebServiceService twss = new TradingWebServiceService();
        TradingWebService tws = twss.getTradingWebServicePort();

        //Use the BindingProvider to authenticate with username and password
        BindingProvider bindingProvider = (BindingProvider) tws;
        bindingProvider.getRequestContext().put(
                BindingProvider.USERNAME_PROPERTY, "csdc23vz_01"
        );
        bindingProvider.getRequestContext().put(
                BindingProvider.PASSWORD_PROPERTY, "uujeit9E"
        );
        //return the setup TradingWebService
        return tws;
    }

    @Override
    public List<String> findStockQuotesByCompanyName(String companyName) {
        TradingWebService tws = createConnection();
        try {
            //Get a List of PublicStockQuotes from the TradingWebService
            var stockQuotes = tws.findStockQuotesByCompanyName(companyName);
            //Format it into a List<String> containing all company names
            var results = new ArrayList<String>();
            for (PublicStockQuote stockQuote: stockQuotes) {
                results.add("\nCompany Name: " + stockQuote.getCompanyName() +
                        "\n Symbol: " + stockQuote.getSymbol() +
                        "\n Stock Exchange: " + stockQuote.getStockExchange() +
                        "\n Last Trade Price: " + stockQuote.getLastTradePrice() +
                        "\n Float Shares: " + stockQuote.getFloatShares() +
                        "\n Last Trade Time: " + stockQuote.getLastTradeTime() +
                        "\n Market Capitalization: " + stockQuote.getMarketCapitalization() +
                        "\n"
                );
            }
            return results;

        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }
}
