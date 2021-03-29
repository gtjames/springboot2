package edu.edgetech.sb2.bootstrap;

import edu.edgetech.sb2.models.Customer;
import edu.edgetech.sb2.services.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;

//	@Component registers this class with Spring -- meaning SB will manage it (create it)
@Component
//							implements ApplicationListener necessary to initialize the app
public class CustomerLoader implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired			//  @Autowired	Use dependency injection to initialize this variable
	private CustomerService customerService;

	private static final Logger log = LogManager.getLogger(ProductLoader.class);

	@Override			//	@Override  my application will create a new version of this method
						//	this method is called when the application wakes up
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//	add in as many dummy records as you would like
		createCustomer("Edge Tech Academy", "2241 S Watson Rd",		 	"682-334-5679");
		createCustomer("Apple Computers", 	"1 Infinite Loop Lane", 	"682-334-5679");
		createCustomer("Ancora Education", 	"8701 Bedford-Euless Rd", 	"682-334-5679");
		createCustomer("American Airlines", "1 American Airlines Rd",	"682-334-5679");
		createCustomer("My New Career", 	"Washington D.C.", 			"801-225-2030");

	}

	public void createCustomer(String name, String address, String phoneNum ) {
		Customer customer;

		//	TODO 	this is only needed if you are creating and dropping the database with each run
		//		otherwise you are adding records to your database over and over again
		customer = new Customer(name, address, phoneNum);
		customerService.saveCustomer(customer);			//	turn off to stop creating Customer records with each run
		log.info(customer);
	}
}
