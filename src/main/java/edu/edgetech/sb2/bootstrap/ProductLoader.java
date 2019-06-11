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
		createProduct("112358132134", 		"Edge Tech Agile", 			"agile.png");
		createProduct("6.022140857×10^23", 	"Edge Tech C#", 			"csharp.png");
		createProduct("8.314459848", 		"Edge Tech CSS", 			"css.png");
		createProduct("1.6021766×10^−19", 	"Edge Tech HTML5", 			"html.png");
		createProduct("137.036", 			"Edge Tech Java 10", 		"java.png");
		createProduct("9.46073047 x 10^12", "Edge Tech JavaScript", 	"javascript.png");
		createProduct("2.718281828459045", 	"Edge Tech MongoDB", 		"mongodb.png");
		createProduct("299792458", 			"Edge Tech MySQL", 			"mysql.png");
		createProduct("13.799±0.021*10^9", 	"Edge Tech Spring Boot", 	"spring.png");
		createProduct("384400", 			"Edge Tech SQL", 			"sql.png");
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

		product = new Product(productId, description, "/images/" + imageUrl, new BigDecimal(Math.random() * 50));
	//	productService.add(product);		//	turned off for now to not keep creating Product records
		log.info(product);
	}
}
