package edu.edgetech.sb2.repositories;

import edu.edgetech.sb2.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	List<Customer> findByPhoneNum(String phoneNum);
}
