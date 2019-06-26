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

@Component
public class CustomerLoader implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private CustomerService customerService;

	private static final Logger log = LogManager.getLogger(ProductLoader.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//	add in as many dummy records as you would like
		createCustomer("Edge Tech Academy", "4421 S Watson Rd",		 	"682-334-5679");
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
		customerService.saveCustomer(customer);			//	turned off for now to not keep creating Customer records
		log.info(customer);
	}
}
