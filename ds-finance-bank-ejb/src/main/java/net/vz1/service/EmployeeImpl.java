package net.vz1.service;

import net.vz1.ejb.common.CustomerInterface;
import net.vz1.ejb.common.EmployeeInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import java.util.List;

@Stateless(name="EmployeeService")
@PermitAll
public class EmployeeImpl extends CustomerImpl implements EmployeeInterface {

    private static final Logger log = LoggerFactory.getLogger(EmployeeImpl.class);

    /*@Inject
    EmployeeDAO employeeDAO;
    @Inject
    EmployeeTranslator employeeTranslator;*/

    public int createCustomer(String firstName, String lastName, String address, String password) {
        /*//Create the customer
        net.vz1.entity.Customer newCustomer = new net.vz1.entity.Customer(firstName, lastName, address, password);
        //add the user into our database
        *//*CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.persist(newCustomer);*//*

        //Try creating WildflyUser
        try {
            WildflyAuthDBHelper wildflyAuthDBHelper = new WildflyAuthDBHelper();
            wildflyAuthDBHelper.addUser("", password, new String[]{"Customer"});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Return the generated customerID
        return newCustomer.getCustomerID();*/
        return 0;
    }

    public CustomerInterface searchCustomerById(int customerId) {
        return null;
    }

    public CustomerInterface searchCustomerByName(String customerName) {
        return null;
    }

    public String buyShares(int customerNumber, String sharesID, int quantity) {
        return null;
    }

    public String sellShares(int customerNumber, String sharesID, int quantity) {
        return null;
    }

    public List<String> getDepotStockQuotes(int customerNumber) {
        return null;
    }

    public float checkInvestableVolume() {
        return 0;
    }
}
