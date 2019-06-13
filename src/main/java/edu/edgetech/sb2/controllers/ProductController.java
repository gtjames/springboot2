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
	 * 			Use a service to limit access to our data repository. ProductRepository is a CrudRepository and had access to
	 * 			all of the features you would expect of a CRUD object, Create, Read, Update AND Delete. Frequently we want to
	 * 			add others and disable other (We rarely delete database records any more). So the service class only opens
	 * 			up the data access methods we want our programs to use.
	 */
	@Autowired
	private ProductService productService;

	/**
	 * 		Environment 	the server environment information. We use this to determine where we will upload files
	 * 						Your local environment is very different that the production sesrver. This allows us to
	 * 						save our files in a relative location and not by specifying an exact folder that may be differnt
	 * 						once we move the code to production
	 */
	@Autowired
	private Environment environment;

	/**
	 *	RequestMethod.GET is the default. It is optional
	 *	This method is a handler for the /product/list request.
	 *		get a list of all Products and add it to the model.
	 *		request the products page and the thymeleaf framework will take care of adding these rows to the page
	 *
	 *		This simple method has many of the steps necessary to get data and send it to a web page
	 *			productService	-	is the connection we have to the database, particularly the Product table
	 *								productService only lets us do so much. It only make visible database actions
	 *								that the designers wanted to give to us. Hidden inside of productService is
	 *								productRepository which opens the door to anything you might consider doing
	 *								to a database entity. If larger projects you only make some of these actions
	 *								visible to control access to the database tables.
	 *			model			-	This is a dictionary object we can add things to which get passed to the web page
	 *								It is a bag we can toss anything in we like. All you need to do is tag the data.
	 *								We just have a single object going to the web page. It is a List of all Products
	 *								in the database. We will tag it with the name 'products'. Go look at the products.html
	 *								page to see that 'products' is referenced and used to populate that page with our
	 *								Product records.
	 *			return "products"	this statement tells our view engine - the code inside of Springboot that takes our
	 *								html file and our model object and merges them together to show our data the
	 *								way the web designers intended.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("products", productService.listAllProducts());
		return "products";
	}

	/**
	 *		this method will handle requests for /product/ followed by an id of the product we want to view
	 */
	@RequestMapping("/{id}")
	public String read(@PathVariable Integer id, Model model){
		//@RequestParam String something  - if there URL has request params ?something=name
		if ( productService.getProductById(id) != null ) {
			//	looks like we found it
			//	take that product and pass it in the model to the web page
			model.addAttribute("product", productService.getProductById(id));
			return "productDetails";
		}
		else {
			//	looks like we did NOT find the product.
			//	show the 404 page
			return "404";
		}
		//	check for a product that matches this id
	}

	/**
	 * 		this method takes one parameter - product id
	 * 		shows the edit page. The productEdit edit page expects and Product object
	 * 		We use the id field to read the database to retrieve this record and pass it along
	 * 		ti the thymeleaf view engine to merge it with the HTML to show our page with the data.
	 */
	@RequestMapping("/edit/{id}")
	public String update(@PathVariable Integer id, Model model){
		model.addAttribute("product", productService.getProductById(id));
		return "productEdit";
	}

	/**
	 * 	this code is very similar to the code above. Except we are creating a NEW Product
	 * 		so we do not read the database we just create an empty Product and pass that to the view engine
	 */
	@RequestMapping("/new")
	public String newProduct(Model model){
		model.addAttribute("product", new Product());
		return "productEdit";
	}

	/**
	 * 		This endpoint is a POST request. We receive data from the web page
	 * 			The user has pressed the submit button on the NEW / EDIT Product page.
	 * 			All of the data for a product is sent to this page which we will save to the database
	 *
	 *		parameters
	 *			Product			This is a Product object. If we play by the rules and name the fields in the HTML file
	 *							with the same names as the entity in the database, then Springboot will be clever enough
	 *							to create a Product object for us filled in with this information
	 *			MultipartFile	If the user selects an image for this Product the browser and server will communicate
	 *							with each other to get that file uploaded to our code, with our assistance.
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Product product, MultipartFile file) {

      	//	fine where we need to save the file on the server. We have a property file that has our configuration info
        String uploadFolder = environment.getProperty("eta.uploadFolder");

        //	load the file and give us back the location of the image to include that in our Proudct record in the database
		String fileName = uploadFile(file, uploadFolder, "images");	// update song path
		product.setImageUrl(fileName);

		productService.saveProduct(product);			//	save to the database (create the record if it does not exist
		return "redirect:/product/list";				//	return "redirect:/product/show/" + product.getId();		to return to the Details page
	}

	/**
	 *
	 * @param file				the file from the browser
	 * @param uploadFolder		the folder to save the file to
	 * @param subfolder			the particular subfolder for the file type
	 * @return					the name of the file to be saved to the database
	 */
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
		return "/" + subfolder + "/" + fileName;
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

	@RequestMapping(value = "/cat/{category}")
	public String byCategory(@PathVariable String category, Model model){
		model.addAttribute("products", productService.findByCategory(category));
		return "products";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam String category, Model model){
		model.addAttribute("products", productService.findByCategory(category));
		return "products";
	}
}
