# springboot
###	 Spring Boot application two - Product pages
#####		Files of note
1.  **ProductLoader.java**		Add data to the database so we have something to see
2.  **ProductController.java**	provides the mapping of the URL in the browser address bar to an actual file
3.  **Product.java**			The Entity definition for the Product table
4.  **ProductRepository.java**	extends the CrudRepository and that is it
5.  **ProductService.java**		Interface: lists our available services
6.  **ProductServiceImpl.java**	Implements the service methods
7.  **pom.xml**					Adding in data and web jars
  1.  **JPA** - We want to access a database - Java Persistent API
  2.  **H2** 	- An in-memory database that makes life and testing easy
  3.  **MySQL** - A real database used by a significant portion of the web world
  4.  **thymeleaf** template - Defines a template for where all of our files are going to be found
  5.  **web jars**
    *  You are going to use these two packages so frequently why worry about which version you are using and where to find it. Let Spring box them up in our jar and provide them to our front end HTML.
    1.  bootstrap - does great things for managing your web page without needing to know which browser or platform you are running on
    2.  jQuery	- This is what the big kids are using on their web site to get data to where they want it.

####		Our application can be found at http://localhost:8080

| Page              | URL                                 	| Method |
|-------------------|-------------------------------------	|--------|
| home page 		| `http://localhost:8080/`          	| GET    |
| list Products		| `http://localhost:8080/products`  	| GET    |
| list Odd Products	| `http://localhost:8080/oddProducts`	| GET    |
| show Product		| `http://localhost:8080/product/{id}`	| GET    |
| edit Product		| `http://localhost:8080/product/edit/{id}`| GET |
| new Product		| `http://localhost:8080/product/new`	| GET    |
| save Product		| `http://localhost:8080/product`		| POST   |
| delete Product	| `http://localhost:8080/product/delete/{id}`| GET |
