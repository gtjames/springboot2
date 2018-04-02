package edu.edgetech.sb2.services;

import edu.edgetech.sb2.domain.Product;
import edu.edgetech.sb2.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

/**
 * Created by Edge Tech Academy on 12/12/2016.
 */
@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
