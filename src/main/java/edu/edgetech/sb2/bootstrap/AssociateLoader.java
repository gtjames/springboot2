package edu.edgetech.sb2.bootstrap;

import edu.edgetech.sb2.domain.Customer;
import edu.edgetech.sb2.domain.Product;
import edu.edgetech.sb2.services.CustomerService;
import edu.edgetech.sb2.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AssociateLoader implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private CustomerService customerService;

	private static final Logger log = LogManager.getLogger(ProductLoader.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		createCustomer("Edge Tech Academy", "8701 Bedford-Euless Rd", 	"682-334-5679");
		createCustomer("First Command", 	"1 First Command Rd", 		"682-334-5679");
		createCustomer("Ancora Education", 	"8701 Bedford-Euless Rd", 	"682-334-5679");
		createCustomer("American Airlines", "1 First Command Rd", 		"682-334-5679");
		createCustomer("Homebid.com", 		"Scotntsdale AZ", 			"801-225-2030");
	}

	public void createCustomer(String name, String address, String phoneNum ) {
		Customer customer;

		customer = new Customer(name, address, phoneNum);
		customerService.saveCustomer(customer);
		log.info(customer);
	}
}
