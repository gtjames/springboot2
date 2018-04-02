package edu.edgetech.sb2.controllers;

import edu.edgetech.sb2.domain.Product;
import edu.edgetech.sb2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Edge Tech Academy on 12/2/2016.
 */
@Controller
public class ProductController {

	//	Use a service for when things get complicated.
	//	more than just doing a findAll or delete

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("products", productService.listAllProducts());
		System.out.println("Returning Products:");
		return "products";
	}

	@RequestMapping("product/{id}")
	public String showProduct(@PathVariable Integer id, Model model){
		if ( productService.getProductById(id) != null ) {
			model.addAttribute("product", productService.getProductById(id));
			return "productshow";
		}
		else return "404";
	}

	//	These items have been added for the new functionality
	//		to make our app a richer MVC example
	@RequestMapping("product/edit/{id}")
	public String edit(@PathVariable Integer id, Model model){
		model.addAttribute("product", productService.getProductById(id));
		return "productform";
	}

	@RequestMapping("product/new")
	public String newProduct(Model model){
		model.addAttribute("product", new Product());
		return "productform";
	}

	@RequestMapping(value = "product", method = RequestMethod.POST)
	public String saveProduct(Product product){
		productService.saveProduct(product);
		return "redirect:/product/" + product.getId();
	}

	@RequestMapping("product/delete/{id}")
	public String delete(@PathVariable Integer id){
		productService.deleteProduct(id);
		return "redirect:/products";
	}

	@RequestMapping(value = "/oddProducts", method = RequestMethod.GET)
	public String listOdd(Model model){
		model.addAttribute("products", productService.listOddProducts());
		System.out.println("Returning products:");
		return "products";
	}
}
