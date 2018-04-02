package edu.edgetech.sb2.bootstrap;

import edu.edgetech.sb2.domain.Product;
import edu.edgetech.sb2.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;

	@Autowired
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	private static final Logger log = LogManager.getLogger(ProductLoader.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

		createProduct("112358132134", 			"Edge Tech Agile", 			"/images/agile.png"		);
		createProduct("6.022140857×10^23",		"Edge Tech C#", 			"/images/csharp.png"	);
		createProduct("8.314459848",			"Edge Tech CSS", 			"/images/css.png"		);
		createProduct("1.6021766×10^−19",	 	"Edge Tech HTML5", 			"/images/html.png" 		);
		createProduct("137.036", 				"Edge Tech Java 1.8", 		"/images/java.png"		);
		createProduct("9.46073047 x 10^12", 	"Edge Tech JavaScript", 	"/images/javascript.png");
		createProduct("2.718281828459045",		"Edge Tech MongoDB", 		"/images/mongodb.png"	);
		createProduct("299792458", 				"Edge Tech MySQL",		 	"/images/mysql.png"		);
		createProduct("13.799±0.021*10^9", 		"Edge Tech Spring Boot",	"/images/spring.png"	);
		createProduct("384400", 				"Edge Tech SQL", 			"/images/sql.png"		);
	}

	public void createProduct(String productId, String description, String imageUrl ) {
		Product product;

		product = new Product(productId, description, imageUrl, new BigDecimal( Math.random()*50 ));
		productRepository.save(product);
		log.info( "id: " + product.getId() + "  " + product.getDescription( ));
	}
}
