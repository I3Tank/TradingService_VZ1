package net.froihofer.dsfinance.bank.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.BindingProvider;

import net.froihofer.dsfinance.ws.trading.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import net.vz1.ejb.common.CustomerInterface;
import net.vz1.ejb.common.TransactionServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for starting the bank client.
 *
 */
public class BankClient {
  private static Logger log = LoggerFactory.getLogger(BankClient.class);

  /**
   * Skeleton method for performing an RMI lookup
   */
  private void getRmiProxy() {
    AuthCallbackHandler.setUsername("tester");
    AuthCallbackHandler.setPassword("testerpass");
    Properties props = new Properties();
    props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
    props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
    try {
      WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");

      CustomerInterface customer = jndiHelper.lookup("CustomerService", CustomerInterface.class);
      var stockQuotes = customer.findAvailableSharesByCompanyName("Apple");

      for (String item : stockQuotes) {
        log.debug(item);
      }
      log.debug("Client actions finished");
      //TODO: Lookup the proxy and assign it to some variable or return it by changing the
      //      return type of this method
    }
    catch (NamingException e) {
      log.error("Failed to initialize InitialContext.",e);
    }
  }

  private void run() {
    //TODO implement the client part
    getRmiProxy();
  }

  public static void main(String[] args) {
    BankClient client = new BankClient();
    client.run();
  }
}
