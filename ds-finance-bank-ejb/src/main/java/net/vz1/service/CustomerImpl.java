package net.vz1.service;

import net.vz1.ejb.common.*;
import net.vz1.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Stateless(name="CustomerService")
@RolesAllowed({"Customer", "Employee"})
public class CustomerImpl implements CustomerInterface {

    private static final Logger log = LoggerFactory.getLogger(CustomerImpl.class);
    @Resource
    private SessionContext sessionContext;

    @Inject
    CustomerDAO customerDAO;
    @Inject
    CustomerTranslator customerTranslator;
    @Inject
    DepotEntryDAO depotEntryDAO;

    @Inject
    BankDAO bankDAO;


    public List<String> findAvailableSharesByCompanyName(String companyName) {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        return transactionService.findStockQuotesByCompanyName(companyName);
    }

    //TODO How to update jpa entity, bei uns quantity?
    //Symbol check for buy if it is a valid aktie
    //Das ganze zeug mit Stockmarket
    //Die Tabellen in der Datenbank aufräumen

    public String buyShares(String symbol, int quantity) throws BankException {
        var customerId = getCustomerID();
        return buyShares(customerId, symbol, quantity);
    }

    @RolesAllowed("Employee")
    public String buyShares(int customerId, String symbol, int quantity) throws BankException{
        TransactionServiceImpl tS = new TransactionServiceImpl();
        //buyShares returns
        var pricePerShare = tS.buyShares(symbol, quantity);

        //pricePerShare returns -1 if the symbol is invalid
        if(Objects.equals(pricePerShare, new BigDecimal(-1))){
            return "Invalid Symbol";
        }

        //else add the bought share to the customer depot
        var customer = customerDAO.findById(customerId);
        var depot = customer.getDepot();
        var entries = depotEntryDAO.findDepotEntriesByDepotId(customer.getDepot().getDepotID());

        DepotEntry currentEntry = null;

        //check if the share is already in the customers depot
        for (DepotEntry entry : entries) {
            if(symbol.contains(entry.getSymbol())){
                currentEntry = entry;
                break;
            }
        }

        try {
            // null => there is no entry yet
            if (currentEntry == null) {
                var newEntry = new DepotEntry(symbol, quantity);
                newEntry.setDepotId(depot.getDepotID());
                depotEntryDAO.persist(newEntry);
                depot.addDepotEntry(newEntry);
            }
            //else update the existing entry
            else {
                currentEntry.setQuantity(currentEntry.getQuantity() + quantity);
                depotEntryDAO.update(currentEntry);
            }

            var bank = bankDAO.getBank();
            var currentVolume = bank.getVolume();
            var newVolume = currentVolume.subtract(pricePerShare.multiply(new BigDecimal(quantity)));
            bank.setVolume(newVolume);
            bankDAO.update(bank);

            return "Share " + symbol + " bought " + quantity + " times for " + pricePerShare + "€ each.";
        } catch (Exception e){
            throw new BankException(e.getMessage());
        }
    }

    public String sellShares(String symbol, int quantity) throws BankException {
        var customerId = getCustomerID();
        return sellShares(customerId, symbol, quantity);
    }

    @RolesAllowed("Employee")
    public String sellShares(int customerId, String symbol, int quantity) throws BankException{
        TransactionServiceImpl tS = new TransactionServiceImpl();
        BigDecimal sellPrice;

        var customer = customerDAO.findById(customerId);
        var depot = customer.getDepot();

        var entries = depotEntryDAO.findDepotEntriesByDepotId(customer.getDepot().getDepotID());

        DepotEntry currentEntry = null;

        for (DepotEntry entry : entries) {
            if(symbol.contains(entry.getSymbol())){
                currentEntry = entry;
                break;
            }
        }

        try {

            if(currentEntry == null){
                return "You do not own this share.";
            } else {
                //Cant sell
                if(currentEntry.getQuantity() < quantity){
                    return "Number of available shares is too low";
                }
                else if(currentEntry.getQuantity() == quantity){
                    sellPrice = tS.sellShares(currentEntry.getSymbol(), quantity);
                    depotEntryDAO.delete(currentEntry);
                }
                //Can sell
                else {
                    sellPrice = tS.sellShares(currentEntry.getSymbol(), quantity);
                    currentEntry.setQuantity(currentEntry.getQuantity() - quantity);
                    depotEntryDAO.update(currentEntry);
                }
            }

            var bank = bankDAO.getBank();
            var currentVolume = bank.getVolume();
            var newVolume = currentVolume.add(sellPrice.multiply(new BigDecimal(quantity)));
            bank.setVolume(newVolume);
            bankDAO.update(bank);

            return "Share " + symbol + " sold " + quantity + " times for " + sellPrice + "€ per share.";
        } catch (Exception e){
            throw new BankException(e.getMessage());
        }
    }

    public List<String> getDepotStockQuotes(){
        var customerId = getCustomerID();
        return getDepotStockQuotes(customerId);
    }

    @RolesAllowed("Employee")
    public List<String> getDepotStockQuotes(int customerId){
        var customer = customerDAO.findById(customerId);
        var depot = customer.getDepot();
        var entries = depotEntryDAO.findDepotEntriesByDepotId(depot.getDepotID());
        var returnList = new ArrayList<String>();

        //Create list with all symbols of the depot
        var symbolsInDepot = new ArrayList<String>();

        for (DepotEntry entry: entries) {
            symbolsInDepot.add(entry.getSymbol());
        }

        //get the stockQuotes according to depot Entries
        TransactionServiceImpl tS = new TransactionServiceImpl();
        var stockQuotes = tS.findStockQuotesBySymbol(symbolsInDepot);
        var totalValue = new BigDecimal(0);

        for (int i = 0; i < entries.size(); i++) {
            var entry = entries.get(i);
            var stockQuote = stockQuotes.get(i);
            var prize = stockQuote.getLastTradePrice();

            returnList.add(entry.getSymbol() + " " + entry.getQuantity() + " " + prize + "€");
            totalValue = totalValue.add(prize.multiply(BigDecimal.valueOf(entry.getQuantity())));
        }

        returnList.add(totalValue.toString());
        return returnList;
    }

    public String searchSharesInDepot(String companyName) {
        return null;
    }

    //-----------------------------Testing
    public List<String> getPublicStockQuotes() {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        return null;
    }

    public Integer getCustomerID(){
        return Integer.parseInt(sessionContext.getCallerPrincipal().getName());
    }
}