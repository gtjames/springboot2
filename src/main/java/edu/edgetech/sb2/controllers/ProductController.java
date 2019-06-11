package edu.edgetech.sb2.controllers;

import edu.edgetech.sb2.models.Product;
import edu.edgetech.sb2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 	ProductController
 * 			Controller		This annotates the ProductController class as a Spring MVC object
 * 							It is intelligent about resolving requests from the browser
 * 			RequestMapping	By adding "/product" to the ProductController class we in effect
 * 							add /product to the start of every request resolvable by the Controller
 * 							Each RequestMapping for the class methods will respond to the value specified
 * 							with /product in front. This allows us to change the ProductController RequestMapping
 * 							in one location and reset the URL endpoints for the whole controller	
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	/**
	 * 		Autowired		When the ProductController object is created the productService attribute will be set by Spring
	 * 						This is dependency injection. The ProductController depends on a connection to the ProductService
	 * 						Spring will see to it that the service is instantiated (created) and set before any requests are made
	 * 						of the ProductController. So the ProductService which provides access to the Product entity in our
	 * 						database, will be available at all times to our methods
	 *
	 * 			Use a service to limit access to our data repositry. ProductRepository is a CrudReposity and had access to
	 * 			all of the features you would expect of a CRUD object, Create, Read, Update AND Delete. Frequently we want to
	 * 			add others and disable other (We rarely delete database records any more). So the service class only opens
	 * 			up the data access methods we want our programs to use.
	 */
	@Autowired
	private ProductService productService;

	@Autowired
	private Environment environment;

	//  RequestMethod.GET is the default. It is optional
	//	This method is a handler for the /product/list request.
	//		get a list of all Products and add it to the model.
	//		request the products page and the thymeleaf framework will take care of adding these rows to the page
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("products", productService.listAllProducts());
		return "products";
	}

	/**
	 *		this method will handle requests for /product/ followed by an id of the product we want to view
	 */
	@RequestMapping("/{id}")
	public String show(@PathVariable Integer id, Model model){
		//@RequestParam String something  - if there URL has request params ?something=name
		if ( productService.getProductById(id) != null ) {
			//	looks like we found it
			//	take that product and pass it in the model to the web page
			model.addAttribute("product", productService.getProductById(id));
			return "productshow";
		}
		else {
			//	looks like we did NOT find the product.
			//	show the 404 page
			return "404";
		}
		//	check for a product that matches this id
	}

	/*
	 * 		this method takes one parameter - product id
	 * 		shows the edit page
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model){
		model.addAttribute("product", productService.getProductById(id));
		return "productEdit";
	}

	@RequestMapping("/new")
	public String newProduct(Model model){
		model.addAttribute("product", new Product());
		return "productEdit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Product product, MultipartFile file) {
        String uploadFolder = environment.getProperty("eta.uploadFolder");
		String fileName = uploadFile(file, uploadFolder, "images");	// update song path
		product.setImageUrl(fileName);

		productService.saveProduct(product);
		return "redirect:/product/list";				//	return "redirect:/product/show/" + product.getId();		to return to the Details page
	}

	public static String uploadFile(MultipartFile file, String uploadFolder, String subfolder) {
		String fileName = null;
		if (! file.isEmpty()) {
			try {
				// Get the file and save it somewhere
				byte[] bytes = file.getBytes();
				fileName = file.getOriginalFilename();
				Path path = Paths.get(".");
				path = Paths.get(path.toAbsolutePath() + uploadFolder + subfolder + "/" + fileName);
				Files.write(path, bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return subfolder + "/" + fileName;
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id){
		productService.deleteProduct(id);
		return "redirect:/product/list";
	}

	//	These items have been added for the new functionality
	//		to make our app a richer MVC example
	@RequestMapping(value = "/oddProducts", method = RequestMethod.GET)
	public String listOdd(Model model){
		model.addAttribute("products", productService.listOddProducts());
		return "products";
	}

	@RequestMapping(value = "/type/{category}")
	public String byType(@PathVariable String category, Model model){
		model.addAttribute("products", productService.findByCategory(category));
		return "products";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam String category, Model model){
		model.addAttribute("products", productService.findByCategory(category));
		return "products";
	}
}
