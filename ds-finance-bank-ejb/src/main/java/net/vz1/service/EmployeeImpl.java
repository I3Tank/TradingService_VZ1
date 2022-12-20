package net.vz1.service;

import net.froihofer.util.jboss.WildflyAuthDBHelper;
import net.vz1.ejb.common.BankException;
import net.vz1.ejb.common.CustomerDTO;
import net.vz1.ejb.common.CustomerInterface;
import net.vz1.ejb.common.EmployeeInterface;
import net.vz1.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.List;


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
    CustomerTranslator customerTranslator;

    public void createCustomer(CustomerDTO customerDTO) throws BankException {
        try {
            //add the user into our database
            try {
                //Insert check id needed.
//                CustomerDTO c = searchCustomerById(customerDTO.getCustomerID());
//                if (c == null) {
                customerDAO.persist(customerTranslator.toEntity(customerDTO));
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
                wildflyAuthDBHelper.addUser(customerDTO.getFirstName(), customerDTO.getPassword(), new String[]{"Customer"});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new BankException(e.getMessage());
        }
    }

    public CustomerDTO searchCustomerById(int customerId) {
        return this.customerTranslator.toDTO(this.customerDAO.findById(customerId));
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

    public void tryAccessEmployee(){
        //Empty Method just to check if we have access or not
    }
}
