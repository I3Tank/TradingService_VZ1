package net.vz1.service;

import net.froihofer.util.jboss.WildflyAuthDBHelper;
import net.vz1.ejb.common.CustomerDTO;
import net.vz1.ejb.common.CustomerInterface;
import net.vz1.ejb.common.EmployeeInterface;
import net.vz1.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Stateless(name="EmployeeService")
@PermitAll
public class EmployeeImpl extends CustomerImpl implements EmployeeInterface {

    private static final Logger log = LoggerFactory.getLogger(EmployeeImpl.class);

    @Inject
    EmployeeDAO employeeDAO;
    @Inject
    EmployeeTranslator employeeTranslator;
    @Inject
    CustomerDAO customerDAO;
    @Inject
    CustomerTranslator customerTranslator;

    public void createCustomer(CustomerDTO customerDTO) {
        //Create the customer
//        Customer newCustomer = new Customer(
//                customerDTO.getFirstName(),
//                customerDTO.getLastName(),
//                customerDTO.getAddress(),
//                customerDTO.getPassword()
//        );

        //add the user into our database
        try {
            Customer c = customerDAO.findById(customerDTO.getFirstName());
            if(c == null){
                customerDAO.persist(customerTranslator.toEntity(customerDTO));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Try creating WildflyUser
        try {
            WildflyAuthDBHelper wildflyAuthDBHelper = new WildflyAuthDBHelper();
            wildflyAuthDBHelper.addUser(customerDTO.getFirstName(), customerDTO.getPassword(), new String[]{"Customer"});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public String testMessage(){
        return "TestMessage!";
    }
}
