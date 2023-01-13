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

  private UserServiceInterface userService;
  private boolean isEmployee = false;
  private CustomerDTO chosenCustomer;

  private boolean applicationRunning = true;

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

      //Search for the user service to determine if the user is employee or customer
      userService = jndiHelper.lookup("UserService", UserServiceInterface.class);

      //Lookup the correct service
      if(userService.isEmployee()){
        employee = jndiHelper.lookup("EmployeeService", EmployeeInterface.class);
        isEmployee = true;
      }
      else {
        customer = jndiHelper.lookup("CustomerService", CustomerInterface.class);
      }
    }

    catch (NamingException e) {
      log.error("Failed to initialize InitialContext.",e);
    }
  }
  private String[] getUserLogin(){
    //Get the userName and password per Command Line Input
    myScanner = new Scanner(System.in);
    System.out.println("Enter Customer ID");
    var userName = myScanner.nextLine();
    System.out.println("Enter password");
    var password = myScanner.nextLine();

    return new String[]{userName, password};
  }

  private void run() throws BankException {
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
  private void handleCustomerInteraction() throws BankException {
    String symbol;
    int quantity;
    while (applicationRunning) {
      System.out.println("My id is: " + customer.getCustomerID());

      System.out.println("Press 1 to search available shares\n");
      System.out.println("Press 2 to buy shares\n");
      System.out.println("Press 3 to sell shares\n");
      System.out.println("Press 4 to list all shares of your depot\n");
      System.out.println("Press X to quit\n");

      var userInput = myScanner.nextLine();
      switch (userInput) {
        case "1":
          var stockQuotes = customer.findAvailableSharesByCompanyName("Apple");
          for (String item : stockQuotes) {
            log.debug(item);
          }
          break;
        case "2":
          System.out.println("Enter Symbol: ");
          symbol = myScanner.nextLine();

          System.out.println("Enter Quantity: ");
          quantity = Integer.parseInt(myScanner.nextLine());

          System.out.println(customer.buyShares(symbol, quantity));
          break;
        case "3":
          System.out.println("Enter Symbol: ");
          symbol = myScanner.nextLine();

          System.out.println("Enter Quantity: ");
          quantity = Integer.parseInt(myScanner.nextLine());

          System.out.println(customer.sellShares(symbol, quantity));
          break;

        case "4":
          var sharesInDepot = customer.getDepotStockQuotes();

          for (int i = 0; i < sharesInDepot.size(); i++) {
            System.out.println(i + " " + sharesInDepot.get(i) + "\n");
          }
          break;
        case "X":
          applicationRunning = false;
          break;
      }
    }
  }
  private void handleEmployeeInteraction() {
    while (applicationRunning) {

      System.out.println("Press 1 to create customer account\n");
      System.out.println("Press 2 to search customer by ID or name\n");
      System.out.println("Press 3 to search available shares\n");
      System.out.println("Press 4 to buy shares for a customer\n");
      System.out.println("Press 5 to sell shares for a customer\n");
      System.out.println("Press 6 to list all shares of customer depot\n");
      System.out.println("Press 7 to check investable volume\n");
      System.out.println("Press X to quit\n");


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
            var id = employee.createCustomer(new CustomerDTO(null, firstName, lastName, address, password));
            System.out.println("Customer with id: " + id + "added successfully");
          } catch (BankException e) {
            throw new RuntimeException(e);
          }
          break;
        case "2":
          System.out.println("Press 1 to search by ID\n");
          System.out.println("Press 2 to search by full name\n");
          var input = myScanner.nextLine();
          switch (input) {
            case "1":
              System.out.println("Enter ID: ");
              var id = Integer.parseInt(myScanner.nextLine());
              var customer = employee.searchCustomerById(id);
              if (customer == null) {
                System.out.println("Invalid ID");
              } else {
                System.out.println("Customer with ID " + id + ": " + customer.getFirstName() + " " + customer.getLastName());
              }
              break;
            case "2":
              System.out.println("Enter full name: ");
              var fullName = myScanner.nextLine();
              var customerList = employee.searchCustomerByName(fullName);

              System.out.println(customerList.size() + " Customer(s) found:\n");

              for (int i = 0; i < customerList.size(); i++) {
                var c = customerList.get(i);
                System.out.println(i + ": " + c.getFirstName() + " " + c.getLastName() + " " + c.getCustomerID() + " " + c.getAddress());
              }
              System.out.println("\n Enter Number of customer: ");
              var customerNumber = Integer.parseInt(myScanner.nextLine());

              chosenCustomer = customerList.get(customerNumber);

              System.out.println("Chosen Customer ID: " + chosenCustomer.getCustomerID());
              break;


          }
        case "X":
          applicationRunning = false;
          break;
      }
    }
  }
  public static void main(String[] args) throws BankException {
    BankClient client = new BankClient();
    client.run();
  }
}