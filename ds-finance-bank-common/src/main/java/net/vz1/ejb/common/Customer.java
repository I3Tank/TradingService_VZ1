package net.vz1.ejb.common;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface Customer {

    public List<String> getPublicStockQuotes();

    public String testMessage();
}
