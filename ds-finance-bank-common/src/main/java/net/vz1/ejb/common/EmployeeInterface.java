package net.vz1.ejb.common;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.List;


@Remote
public interface EmployeeInterface extends CustomerInterface {

    /*
        In Customer:
        Suchen nach verfügbaren Aktien.
    */

    //Anlegen von Kunden, wobei für einen Kunden mindestens Vor- und Nachname, Adresse und eine Kundennummer vergeben werden.
    public Integer createCustomer(CustomerDTO customerDTO) throws BankException;

    //Suchen nach Kunden mittels Kundennummer oder Name des Kunden. Gehen Sie dabei davon aus, dass Kunden nicht immer ihre Kundennummer wissen.
    public CustomerDTO searchCustomerById(int customerId);

    public List<CustomerDTO> searchCustomerByName(String[] customerName);

    //Kaufen von Aktien für einen Kunden.
    public String buySharesForCustomer(int customerNumber, String sharesID, int quantity) throws BankException;

    //Verkaufen von Aktien für einen Kunden.
    public String sellSharesForCustomer(int customerNumber, String symbol, int quantity) throws BankException;

    //Auflisten aller Aktienanteile im Depot eines Kunden inkl. aktuellem Wert pro Firma und Gesamtwert des Depots.
    public List<String> getDepotStockQuotesForCustomer(int customerNumber);

    //Abfrage des aktuell investierbaren Volumens der Bank an der Börse.
    public BigDecimal checkInvestableVolume();

}
