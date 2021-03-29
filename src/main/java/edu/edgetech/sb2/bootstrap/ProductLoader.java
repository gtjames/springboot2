package edu.edgetech.sb2.bootstrap;

import edu.edgetech.sb2.models.Product;
import edu.edgetech.sb2.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private ProductService productService;

	private static final Logger log = LogManager.getLogger(ProductLoader.class);

	/*
	 *		 onApplicationEvent
	 *			This method will be called when the application gets loaded
	 *			It is a handler for the start up event
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//	see down below we have a small method to create Products for us
		createProduct("12345", 	"Edge Tech Agile", 			"/images/agile.png"		);
		createProduct("02468",	"Edge Tech C#", 			"/images/csharp.png"	);
		createProduct("13579",	"Edge Tech CSS", 			"/images/css.png"		);
		createProduct("024816",	"Edge Tech HTML5", 			"/images/html.png" 		);
		createProduct("54321", 	"Edge Tech Java 1.8", 		"/images/java.png"		);
		createProduct("00001", 	"Edge Tech JavaScript", 	"/images/javascript.png");
		createProduct("ABCDE",	"Edge Tech MongoDB", 		"/images/mongodb.png"	);
		createProduct("AEIOU", 	"Edge Tech MySQL",		 	"/images/mysql.png"		);
		createProduct("VWXYZ",	"Edge Tech Spring Boot",	"/images/spring.png"	);
		createProduct("KLMNO",	"Edge Tech SQL", 			"/images/sql.png"		);
	}

	/*
	 *		createProduct
	 *			two step process here
	 *			create the POJO.
	 *			add the new product to our productService object
	 *				this will take care of all the rest of the work of creating the row in the database
	 */
	public void createProduct(String productId, String description, String imageUrl) {
		Product product;

		//	TODO 	this is only needed if you are creating and dropping the database with each run
		//		otherwise you are adding records to your database over and over again
		product = new Product(productId, description, imageUrl, (float)Math.random() * 50 + 10);
		productService.add(product);		//	comment to stop creating Product records
		log.info(product);
	}
}
