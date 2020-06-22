package edu.edgetech.sb2.services;

		import edu.edgetech.sb2.models.Customer;
		import edu.edgetech.sb2.repositories.CustomerRepository;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.stereotype.Service;

		import java.util.Iterator;
		import java.util.Optional;

/**
 * 	TODO The CustomerService object provides access to the Customer entity in the database
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	//	FindAll is a easy means to collect all records from the database
	public Iterable<Customer> listAllCustomers() {
		return customerRepository.findAll();
	}

	//	TODO Check this out. The findById method returns an Optional object
	//		it is a wrapper for a Customer object
	//		if the findById found a customer record
	//		then return the record
	//		ELSE return null
	public Customer getCustomerById(Integer id) {
		Optional<Customer> oCustomer = customerRepository.findById(id);
		return oCustomer.orElse(null);
	}

	//	TODO This will update or insert a record in the database
	//		notice the return customer;
	//		why return the record you just saved?
	//		because if this is an insert then the ID field has not yet been updated
	//		and this returns the new record with the updated id and lastUpdated timestamp
	public Customer saveCustomer(Customer customer) {
		customerRepository.save(customer);
		return customer;
	}

	//	TODO	Here is another example of the Optional object
	//			note the ifPresent method on the Optional customer object
	public void deleteCustomer(Integer id) {
		Optional<Customer> oCustomer = customerRepository.findById(id);
		oCustomer.ifPresent(customer -> customerRepository.delete(customer));
	}

	//	TODO go to the CustomerRepository class and see how many lines of code were used for this search
	public Iterable<Customer> findByPhoneNum(String phoneNum) {
		return customerRepository.findByPhoneNum(phoneNum);
	}

	public Iterable<Customer> listOddCustomers() {
		Iterable<Customer> Customers = customerRepository.findAll();
		for(Iterator<Customer> prodIterator = Customers.iterator(); prodIterator.hasNext();) {
			Customer prod = prodIterator.next();
			if ((prod.getId() & 1) == 0)		// if the Id is Even
				prodIterator.remove();			// remove from our list
		}
		return Customers;
	}
}
