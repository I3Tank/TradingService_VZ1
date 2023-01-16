package net.vz1.service;

import net.froihofer.util.jboss.WildflyAuthDBHelper;
import net.vz1.ejb.common.*;
import net.vz1.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@Stateless(name="EmployeeService")
@RolesAllowed("Employee")
public class EmployeeImpl extends CustomerImpl implements EmployeeInterface {

    private static final Logger log = LoggerFactory.getLogger(EmployeeImpl.class);

    @Inject
    EmployeeDAO employeeDAO;
    @Inject
    EmployeeTranslator employeeTranslator;
    @Inject
    CustomerDAO customerDAO;
    @Inject
    DepotDAO depotDAO;
    @Inject
    CustomerTranslator customerTranslator;
    @Inject
    DepotTranslator depotTranslator;
    @Inject
    BankDAO bankDAO;

    public Integer createCustomer(CustomerDTO customerDTO) throws BankException {
        var newCustomer = customerTranslator.toEntity(customerDTO);
        try {
            //add the user into our database
            try {
                //Insert check id needed.
//                CustomerDTO c = searchCustomerById(customerDTO.getCustomerID());
//                if (c == null) {

                customerDAO.persist(newCustomer);
//                }
//                else {
//                    log.warn("ID already taken.");
//                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //Try creating WildflyUser
            try {
                WildflyAuthDBHelper wildflyAuthDBHelper = new WildflyAuthDBHelper();
                wildflyAuthDBHelper.addUser(newCustomer.getCustomerID().toString(), customerDTO.getPassword(), new String[]{"Customer"});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {

                //create Depot for the customer
                DepotDTO depotDTO = new DepotDTO(null, newCustomer.getCustomerID());
                depotDAO.persist(depotTranslator.toEntity(depotDTO));
            }
            catch (Exception e){
                throw new BankException(e.getMessage());
            }
        } catch (Exception e) {
            throw new BankException(e.getMessage());
        }
        return newCustomer.getCustomerID();
    }

    public CustomerDTO searchCustomerById(int customerId) {
        return this.customerTranslator.toDTO(this.customerDAO.findById(customerId));
    }

    public List<CustomerDTO> searchCustomerByName(String[] fullName) {
        //Get list of customer from database
        var customerList = this.customerDAO.findByFullName(fullName);
        //convert list to customerDTO list
        return this.customerTranslator.toDTOList(customerList);
    }

    public String buySharesForCustomer(int customerId, String symbol, int quantity) throws BankException {
        return buyShares(customerId, symbol, quantity);
    }

    public String sellSharesForCustomer(int customerId, String symbol, int quantity) throws BankException {
        return sellShares(customerId, symbol, quantity);
    }

    public List<String> getDepotStockQuotesForCustomer(int customerId) {
        return getDepotStockQuotes(customerId);
    }

    public BigDecimal checkInvestableVolume() {
        //bankDAO.persist(new Bank(new BigDecimal(1000000000)));
        return bankDAO.getBank().getVolume();
    }
}
