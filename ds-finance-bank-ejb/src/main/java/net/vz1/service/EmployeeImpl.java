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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public void createCustomer(CustomerDTO customerDTO) throws BankException {
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
                wildflyAuthDBHelper.addUser(customerDTO.getFirstName(), customerDTO.getPassword(), new String[]{"Customer"});
                //create Depot for the customer
                DepotDTO depotDTO = new DepotDTO(newCustomer.getCustomerID(), null, null);
                depotDAO.persist(depotTranslator.toEntity(depotDTO));
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

    public List<CustomerDTO> searchCustomerByName(String fullName) {
        //[0] = FirstName   [1] = LastName
        var splitName = fullName.split(" ");
        //Get list of customer from database
        var customerList = this.customerDAO.findByFullName(splitName);
        //convert list to customerDTO list
        return this.customerTranslator.toDTOList(customerList);
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
