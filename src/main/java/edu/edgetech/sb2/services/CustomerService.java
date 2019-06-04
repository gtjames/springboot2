package edu.edgetech.sb2.services;

import edu.edgetech.sb2.domain.Customer;
import edu.edgetech.sb2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Iterable<Customer> listAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomerById(Integer id) {
		Optional<Customer> oCustomer = customerRepository.findById(id);
		return oCustomer.orElse(null);
	}

	public Customer saveCustomer(Customer customer) {
		customerRepository.save(customer);
		return customer;
	}

	public void deleteCustomer(Integer id) {
		Optional<Customer> oCustomer = customerRepository.findById(id);
		oCustomer.ifPresent(customer -> customerRepository.delete(customer));
	}

	public Iterable<Customer> findByPhoneNum(String type) {
		return customerRepository.findByPhoneNum(type);
	}

}
