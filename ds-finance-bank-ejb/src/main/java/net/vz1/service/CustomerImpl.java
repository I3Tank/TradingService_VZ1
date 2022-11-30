package net.vz1.service;

import net.vz1.ejb.common.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="CustomerService")
@PermitAll
public class CustomerImpl  implements Customer{

    private static final Logger log = LoggerFactory.getLogger(CustomerImpl.class);

    public List<Object> getPublicStockQuotes() {
        return null;
    }

    public String testMessage() {
        return "This is the Test Message";
    }
}
