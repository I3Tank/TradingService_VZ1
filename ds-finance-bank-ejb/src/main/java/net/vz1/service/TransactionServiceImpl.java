package net.vz1.service;

import net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.vz1.ejb.common.TransactionServiceInterface;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.util.List;

@WebService(endpointInterface = "net.froihofer.dsfinance.ws.trading.TradingWebService",
        serviceName = "TransactionService", portName = "TransactionServicePort")
public class TransactionServiceImpl implements TradingWebService {

    @Override
    public BigDecimal buy(String symbol, int shares) throws TradingWSException_Exception {
        return null;
    }

    @Override
    public List<PublicStockQuote> findStockQuotesByCompanyName(String partOfCompanyName) throws TradingWSException_Exception {
        return null;
    }

    @Override
    public BigDecimal sell(String symbol, int shares) throws TradingWSException_Exception {
        return null;
    }

    @Override
    public List<PublicStockQuote> getStockQuotes(List<String> symbols) throws TradingWSException_Exception {
        return null;
    }

    @Override
    public List<PublicStockQuote> getStockQuoteHistory(String symbol) throws TradingWSException_Exception {
        return null;
    }
}
