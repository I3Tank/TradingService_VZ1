package net.froihofer.dsfinance.bank.client;

import java.util.*;
import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import net.vz1.ejb.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for starting the bank client.
 *
 */
public class BankClient {
  private static Logger log = LoggerFactory.getLogger(BankClient.class);
  private Scanner myScanner;

  private CustomerInterface customer;
  private EmployeeInterface employee;
  private boolean isEmployee = false;
  /**
   * Skeleton method for performing an RMI lookup
   */
  private void getRmiProxy(String[] userCredentials) {
    //use userCredentials for the connection
    AuthCallbackHandler.setUsername(userCredentials[0]);
    AuthCallbackHandler.setPassword(userCredentials[1]);
    Properties props = new Properties();
    props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
    props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
    //try to establish the connection
    try {
      WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");

      //Search for the bean services
      customer = jndiHelper.lookup("CustomerService", CustomerInterface.class);
      employee = jndiHelper.lookup("EmployeeService", EmployeeInterface.class);

      //Try if the client has access to the customer bean
      try {
        customer.tryAccessCustomer();
      }
      //If the user has no access to the customer bean => is an employee
      catch (EJBAccessException e){
        isEmployee = true;
      }
      //TODO: Lookup the proxy and assign it to some variable or return it by changing the
      //      return type of this method
    }

    catch (NamingException e) {
      log.error("Failed to initialize InitialContext.",e);
    }
  }
  private String[] getUserLogin(){
    //Get the userName and password per Command Line Input
    myScanner = new Scanner(System.in);
    System.out.println("Enter username");
    var userName = myScanner.nextLine();
    System.out.println("Enter password");
    var password = myScanner.nextLine();

    return new String[]{userName, password};
  }

  private void run() {
    //TODO implement the client part
    var userCredentials = getUserLogin();
    getRmiProxy(userCredentials);

    //Check if the account exists
    if(customer == null && employee == null){
      System.out.println("Invalid Credentials");
      return;
    }
    //From here now we know if we have a Customer or Employee
    if(isEmployee){
      //Do Employee Routine
      System.out.println("Logged in as Employee");
      handleEmployeeInteraction();
    }
    else {
      //Do Customer Routine
      System.out.println("Logged in as Customer");
      handleCustomerInteraction();
    }
  }
  private void handleCustomerInteraction(){
    System.out.println("Press 1 for Apple StockQuotes");
    var userInput = myScanner.nextLine();
    switch (userInput) {
      case "1":
        var stockQuotes = customer.findAvailableSharesByCompanyName("Apple");
        for (String item : stockQuotes) {
          log.debug(item);
        }
        break;
    }
  }
  private void handleEmployeeInteraction(){
    System.out.println("Press 1 to add a customer account");
    var userInput = myScanner.nextLine();
    switch (userInput) {
      case "1":
        //Creating an account
        System.out.print("First Name: ");
        var firstName = myScanner.nextLine();
        System.out.print("\nLast Name: ");
        var lastName = myScanner.nextLine();
        System.out.print("\nAddress: ");
        var address = myScanner.nextLine();
        System.out.print("\nPassword: ");
        var password = myScanner.nextLine();

        try {
          employee.createCustomer(new CustomerDTO(firstName, lastName, address, password));
          System.out.println("Customer added successfully");
        }
        catch (BankException e) {
          throw new RuntimeException(e);
        }

        break;
    }
  }

  public static void main(String[] args) {
    BankClient client = new BankClient();
    client.run();
  }
}