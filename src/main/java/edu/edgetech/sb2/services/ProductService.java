package edu.edgetech.sb2.services;

import edu.edgetech.sb2.models.Product;
import edu.edgetech.sb2.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 		TODO	ProductService
 * 				These are the APIs we expose to the Controllers
 * 				We pretty much have all of them enable. Frequently you would change how the delete code works
 * 				probably by setting an 'active' attribute to false and saving the record and NOT deleting anything
 */
@Service
public class ProductService {

	@Autowired
    private ProductRepository productRepository;

    public Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
		Optional<Product> oProduct = productRepository.findById(id);
		return oProduct.orElse(null);
    }

	//	These items have been added for the new functionality
	//		to make our app a richer MVC example
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProduct(Integer id) {
		Optional<Product> oProduct = productRepository.findById(id);
		oProduct.ifPresent(product -> productRepository.delete(product));
	}

	public Product add(Product product) {
		productRepository.save(product);
		return product;
	}

	public Iterable<Product> findByCategory(String category) {
		return productRepository.findByCategory(category);
	}

	public Iterable<Product> listOddProducts() {
		Iterable<Product> products = productRepository.findAll();
		for(Iterator<Product> prodIterator = products.iterator(); prodIterator.hasNext();) {
			Product prod = prodIterator.next();
			if ((prod.getId() & 1) == 0)		// if the Id is Even
				prodIterator.remove();			// remove from our list
		}
		return products;
	}
}
