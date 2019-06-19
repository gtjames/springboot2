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
		createProduct("123", 				"Edge Tech Agile", 			"agile.png");
		createProduct("186282", 			"Edge Tech C#", 			"csharp.png");
		createProduct("color:blue",	 		"Edge Tech CSS", 			"css.png");
		createProduct("<HTML>",			 	"Edge Tech HTML5", 			"html.png");
		createProduct("Version 12", 		"Edge Tech Java",	 		"java.png");
		createProduct("Must know!",			"Edge Tech JavaScript", 	"javascript.png");
		createProduct("No SQL",			 	"Edge Tech MongoDB", 		"mongodb.png");
		createProduct("CREATE Database X",	"Edge Tech MySQL", 			"mysql.png");
		createProduct("@Cool",			 	"Edge Tech Spring Boot", 	"spring.png");
		createProduct("SELECT * FROM",		"Edge Tech SQL", 			"sql.png");
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
		float price = (int)((Math.random() * 5000) + 2000);
		price /= 100;
		product = new Product(productId, description, "/images/" + imageUrl, new BigDecimal(price));
		productService.add(product);		//	turned off for now to not keep creating Product records
		log.info(product);
	}
}
