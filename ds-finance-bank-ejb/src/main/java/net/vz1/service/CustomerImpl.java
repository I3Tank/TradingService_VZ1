package net.vz1.service;

import net.vz1.ejb.common.BankException;
import net.vz1.ejb.common.CustomerInterface;
import net.vz1.ejb.common.DepotEntryDTO;
import net.vz1.entity.CustomerDAO;
import net.vz1.entity.CustomerTranslator;
import net.vz1.entity.DepotEntry;
import net.vz1.entity.DepotEntryDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


@Stateless(name="CustomerService")
@RolesAllowed("Customer")
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


    public List<String> findAvailableSharesByCompanyName(String companyName) {
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        return transactionService.findStockQuotesByCompanyName(companyName);
    }

    //TODO How to update jpa entity, bei uns quantity?
    //Symbol check for buy if it is a valid aktie
    //Das ganze zeug mit Stockmarket
    //Die Tabellen in der Datenbank aufräumen

    public String buyShares(String symbol, int quantity) throws BankException {
        var customer = customerDAO.findById(getCustomerID());
        var depot = customer.getDepot();
        var entries = depotEntryDAO.findDepotEntriesByDepotId(customer.getCustomerID());

        DepotEntry currentEntry = null;

        for (DepotEntry entry : entries) {
            if(entry.getSymbol() == symbol){
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
            } else {
                currentEntry.setQuantity(currentEntry.getQuantity() + quantity);
            }

            return "Share " + symbol + " bought " + quantity + " times.";
        } catch (Exception e){
            throw new BankException(e.getMessage());
        }
    }

    public String sellShares(String symbol, int quantity) throws BankException {
        var customer = customerDAO.findById(getCustomerID());
        var depot = customer.getDepot();

        var entries = depotEntryDAO.findDepotEntriesByDepotId(customer.getCustomerID());

        DepotEntry currentEntry = null;

        for (DepotEntry entry : entries) {
            if(entry.getSymbol() == symbol){
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
                depotEntryDAO.delete(currentEntry);
                return "All shares of " + currentEntry.getSymbol() + " sold.";
            }
            //Can sell
            else {
                currentEntry.setQuantity(currentEntry.getQuantity() - quantity);
            }
        }

        return "Share " + symbol + " sold " + quantity + " times.";
        } catch (Exception e){
            throw new BankException(e.getMessage());
        }
    }

    public List<String> getDepotStockQuotes() {
        var customer = customerDAO.findById(getCustomerID());
        var depot = customer.getDepot();
        var entries = depot.getAllDepotEntries();

        var stringList = new ArrayList<String>();

        for (DepotEntry entry: entries) {
            stringList.add(entry.getSymbol() + " " + entry.getQuantity());
        }
        return stringList;
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