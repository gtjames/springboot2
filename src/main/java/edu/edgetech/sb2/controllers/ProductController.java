package edu.edgetech.sb2.controllers;

import edu.edgetech.sb2.models.Product;
import edu.edgetech.sb2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * 	ProductController
 * 			Controller		This annotates the ProductController class as a Spring MVC object
 * 							It is intelligent about resolving requests from the browser
 * 			RequestMapping	By adding "/product" to the ProductController class we in effect
 * 							add /product to the start of EVERY request handeled by ProductController
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
	 * 			A service is used to limit access to the repository. ProductRepository is a CrudRepository and has access to
	 * 			all of the features you would expect of a CRUD object, Create, Read, Update AND Delete. Frequently we want to
	 * 			add others and disable others (We rarely delete database records any more). So the service class only opens
	 * 			up the data access methods we want our programs to use.
	 */
	@Autowired
	private ProductService productService;

	/**
	 * 		Environment 	Provides access the the application.properties file. The getProperty method will retrieve
	 * 						the value of a property which we can use in the code. For instance in this Controller
	 * 						we are interested in the destination folder for the images we will be uploading. We save
	 * 						the folder in the properties files. This property is consistent and available to any 
	 * 						code that wishes to use these same folders	
	 */
	@Autowired
	private Environment environment;

	/**
	 *   RequestMapping - This method is the handler for the /product/list request.
	 *   
	 *		get a list of all Products and add it to the model.
	 *		request the products page and the thymeleaf framework will take care of adding these rows to the page
	 *
	 * 	 	RequestMethod.GET is the default. It is optional
	 * 	 	
	 *		This method has many of the steps necessary to get data and send it to a web page
	 *			productService	-	is the connection we have to the database, particularly the Product table.
	 *								productService only lets us do so much. It only make visible database actions
	 *								that the designers wanted to give to us. Hidden inside of productService is
	 *								productRepository which opens the door to anything you might consider doing
	 *								to a database entity. In larger projects you only make some of these actions
	 *								visible to limit access what you can do to the database tables.
	 *			model			-	This is a dictionary object. We can add things to it, which get passed to the web page
	 *								It is a bag we can toss anything in we like. All you need to do is tag the data.
	 *								In this code we have just a single object going to the web page. It is a List of all Products
	 *								in the database. We will tag it with the name 'products'. Go look at the products.html
	 *								page to see that 'products' is referenced and used to populate that page with our
	 *								Product records.
	 *			return "products"	The thymeleaf view engine finds products.html and merges it with the model data items
	 *								to create our web page HTML + data
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
	}

	/**
	 * 		this method takes one parameter - product id
	 * 		shows the edit page. The productEdit edit page expects a Product object
	 * 		We use the id field to read the database to retrieve this record and pass it along
	 * 		to the thymeleaf view engine to merge it with the HTML to show our page with the data.
	 */
	@RequestMapping("/edit/{id}")
	public String update(@PathVariable Integer id, Model model){
		model.addAttribute("product", productService.getProductById(id));
		return "productEdit";
	}

	/**
	 * 		this code is very similar to the code above. Except we are creating a NEW Product
	 * 		so we do not read the database we just create an empty Product and pass that to the view engine
	 */
	@RequestMapping("/new")
	public String newProduct(Model model){
		model.addAttribute("product", new Product());
		return "productEdit";
	}

	/**
	 * 			This endpoint is a POST request. We receive data from the web page
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

      	//	find where we need to save the file on the server. application.properties has an property call eta.uploadFolder
		//	it is defined with the folder destination for our upload files 
        String uploadFolder = environment.getProperty("eta.uploadFolder");

        //	load the file and give us back the location of the image to include that in our Product record in the database
		if (! file.isEmpty()) {
			String fileName = uploadFile(file, uploadFolder, "images");
			product.setImageUrl(fileName);									//	update imageUrl property with our image 
		}
		productService.saveProduct(product);			//	save to the database (create the record if it does not exist
		return "redirect:/product/list";				//	return "redirect:/product/show/" + product.getId();		to return to the Details page
	}

	/**
	 *		uploadFile
	 * @param file				the file from the browser
	 * @param uploadFolder		the folder to save the file to
	 * @param subfolder			the particular subfolder for the file type
	 * @return					the name of the file to be saved to the database
	 */
	public static String uploadFile(MultipartFile file, String uploadFolder, String subfolder) {
		String fileName = null;
		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();				//	read the entire file into this buffer
			fileName = file.getOriginalFilename();		//	get the name of the file being uploaded
			
			Path path = Paths.get(".");					//	what is the current directory?
														//	build a path to the upload folder
			path = Paths.get(path.toAbsolutePath() + uploadFolder + subfolder + "/" + fileName);
			Files.write(path, bytes);					//	save the file to the upload folder
		} catch (IOException e) {
			e.printStackTrace();						//	just in case things go bad
		}
		return "/" + subfolder + "/" + fileName;		//	return the 'relative' location of the file
	}

	/**
	 *		delete - delete this product from the database
	 *					when complete redirect the user to the product /list page
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id){
		productService.deleteProduct(id);
		return "redirect:/product/list";
	}

	//	These items have been added for the new functionality
	//		to make our app a richer MVC example
	@RequestMapping(value = "/oddProducts", method = GET)
	public String listOdd(Model model){
		model.addAttribute("products", productService.listOddProducts());
		return "products";
	}

	/**
	 *		/product/cat/#
	 *		Display all products matching this category 	
 	 */
	@RequestMapping(value = "/cat/{category}")
	public String byCategory(@PathVariable String category, Model model){
		//	the findByCategory is a automatically generated method. Spring will create the SELECT statement
		//	necessary to read Products WHERE Category equals the asked for
		//	As long as the XXXXX in findByXXXXX matches the name of a property of the Product table Spring will do the rest 
		model.addAttribute("products", productService.findByCategory(category));
		return "products";
	}

	/**
	 * 		search 
	 * 			This time we are using the POST method. Which means the data for this request is coming from
	 * 			the body of the web page. The web page must have a named field called category. If so Spring does the rest
	 * 			That data will be assigned to our category parameter. We read the database, save the list of Products
	 * 			to the products attribute of the model object, and tell thyme to merge that with the products.html page	
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam String category, Model model){
		model.addAttribute("products", productService.findByCategory(category));
		return "products";
	}

	/**
	 * 		TODO	The rest of these endpoints are for educational purposes
	 * 				All of these endpoints are annotated with @ResponseBody. This means what ever we return
	 * 				with the return statement is exactly and only what will be displayed in the browser.
	 * 				This makes for some great experimentation	.
	 * 	
	 * 				/product/string/{any text we want} 
	 * 					assigns the text following /product/string/{XXXXXX} to the line parameter
	 * 					Then we can do whatever is required with that data
	 * 					
	 *				/product/query?id={some text}&string={some other text}&number={an optional NUMBER}
	 *					This endpoint has three possible parameters passed to it. 
	 *					The first two are required and number is optional
	 *				
	 *				/products/vowels/{some text}
	 *					we are going to count the vowels in the string passed into the endpoint
	 *					along the way we will also save the consonants that are obviously not vowels
	 *				
	 *				/products/json/{product id}
	 *					read the Product table and retrieve a record
	 *					return that to the web page. Because it is marked as ResponseBody it will get bundled up
	 *					like a JSON object and sent to the page
	 *
	 *				/products/tree/{how many limbs}
	 *					let's practice for that job interview question
	 * 
	 */
	@RequestMapping(value = "/string/{line}")
	@ResponseBody
	public String result(@PathVariable String line) {
		return line;
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public String query(@RequestParam String id,							//	'id' param is required
						@RequestParam("string") String input,				//	'string' param is required. the variable used is input
						@RequestParam("number") Optional<Integer> number) {	//	'itemid' param is optional
		return "These are the Query params. id: " + id + " string: " + input + " and number: " + (number.isPresent() ? number.get() : "not passed in");
	}

	@RequestMapping(value="/vowels/{line}", method=GET)
	@ResponseBody
	public String vowels(@PathVariable String line) {
		int vowels = 0;
		String list = "";
		for (int i = 0; i < line.length(); i++) {
			if ( "aeiou".contains(""+line.charAt(i))) {
				vowels++;
			}
			else {
				list += line.charAt(i);
			}
		}
		return "'" + line + "' has " + vowels + " vowels. Here are the remaining consonants " + list;
	}

	@RequestMapping(value="/json/{id}", method=GET)
	@ResponseBody
	public Product json(@PathVariable Integer id){
		Product prod = productService.getProductById(id);
		return prod;
	}

	@RequestMapping("/tree/{levels}")
	@ResponseBody
	public String tree(@PathVariable Integer levels) {
		String strTree = "";
		String row = new String(new char[levels-1]).replace  ("\0", " ") +
					 new String(new char[levels*2-1]).replace("\0", "*");
		for ( int i = 1; i <= levels; i++ ) {
			strTree += row.substring(i-1, levels-1+2*i-1) + "<br>";
		}
		return "<pre>" + strTree + "</pre>";
	}
}
