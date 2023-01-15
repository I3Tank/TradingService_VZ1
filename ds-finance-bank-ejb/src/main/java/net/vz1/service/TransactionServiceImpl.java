package net.vz1.service;

import net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import net.vz1.ejb.common.StockQuoteDTO;
import net.vz1.ejb.common.TransactionServiceInterface;

import javax.xml.ws.BindingProvider;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public List<StockQuoteDTO> findStockQuotesBySymbol(List<String> symbol) {
        TradingWebService tws = createConnection();
        var stockQuoteDTOs = new ArrayList<StockQuoteDTO>();
        try {
            var publicStockQuotes = tws.getStockQuotes(symbol);

            //Translate PublicStockQuotes => StockQuoteDTOs
            for (PublicStockQuote publicStockQuote : publicStockQuotes) {
                stockQuoteDTOs.add(new StockQuoteDTO(
                        publicStockQuote.getCompanyName(),
                        publicStockQuote.getFloatShares(),
                        publicStockQuote.getLastTradePrice(),
                        publicStockQuote.getLastTradeTime(),
                        publicStockQuote.getMarketCapitalization(),
                        publicStockQuote.getStockExchange(),
                        publicStockQuote.getSymbol()
                ));
            }
            return stockQuoteDTOs;
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal buyShares(String symbol, int quantity) {
        TradingWebService tws = createConnection();
        try {
            //check if the provided symbols are valid
            if(!checkIfValidSymbol(symbol)){ return new BigDecimal(-1); }

            //if valid buy it and return the price per share
            return tws.buy(symbol, quantity);
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfValidSymbol(String symbol) {
        TradingWebService tws = createConnection();
        try {
            var stockQuotes = tws.getStockQuotes(Collections.singletonList(symbol));
            //if 0 => invalid Symbol
            if (stockQuotes.size() == 0){
                return false;
            }
            else {
                return true;
            }

        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal sellShares(String symbol, int quantity) {
        TradingWebService tws = createConnection();
        try {
            return tws.sell(symbol, quantity);
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }
}
