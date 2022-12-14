package net.vz1.ejb.common;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CustomerInterface {

    //Suche nach verfügbaren Aktien
    public List<String> findAvailableSharesByCompanyName(String companyName);

    //Kaufen von Aktien
    public String buyShares(int sharesID, int quantity);

    //Verkaufen von Aktien
    public String sellShares(int sharesID, int quantity);

    //Auflisten aller Aktienanteile im Depot inkl. aktuellem Wert pro Firma und Gesamtwert des Depots
    public List<String> getDepotStockQuotes();

    public String searchSharesInDepot(String companyName);

    //-----------------------------Testing
    public List<String> getPublicStockQuotes();

    public void tryAccessCustomer();
}
