package edu.edgetech.sb2.repositories;

import edu.edgetech.sb2.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	List<Customer> findByPhoneNum(String type);
}
