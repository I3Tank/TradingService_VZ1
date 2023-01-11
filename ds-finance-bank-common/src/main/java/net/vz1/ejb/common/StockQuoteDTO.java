package net.vz1.ejb.common;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class StockQuoteDTO {
    protected String companyName;
    protected Long floatShares;
    protected BigDecimal lastTradePrice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastTradeTime;
    protected Long marketCapitalization;
    protected String stockExchange;
    protected String symbol;

    public StockQuoteDTO(String companyName, Long floatShares, BigDecimal lastTradePrice, XMLGregorianCalendar lastTradeTime, Long marketCapitalization, String stockExchange, String symbol) {
        this.companyName = companyName;
        this.floatShares = floatShares;
        this.lastTradePrice = lastTradePrice;
        this.lastTradeTime = lastTradeTime;
        this.marketCapitalization = marketCapitalization;
        this.stockExchange = stockExchange;
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Long getFloatShares() {
        return floatShares;
    }

    public BigDecimal getLastTradePrice() {
        return lastTradePrice;
    }

    public XMLGregorianCalendar getLastTradeTime() {
        return lastTradeTime;
    }

    public Long getMarketCapitalization() {
        return marketCapitalization;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public String getSymbol() {
        return symbol;
    }
}
