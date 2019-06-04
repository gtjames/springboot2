package edu.edgetech.sb2.controllers;

import edu.edgetech.sb2.domain.Customer;
import edu.edgetech.sb2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	//  RequestMethod.GET is the default. It is optional
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("customers", customerService.listAllCustomers());
		return "customers";
	}

	@RequestMapping("customer/{id}")
	public String show(@PathVariable Integer id, Model model){
		//@RequestParam String thing,
		Customer cust = customerService.getCustomerById(id);
		if ( cust != null ) {
			model.addAttribute("customer", cust);
			return "customershow";
		}
		else {
			model.addAttribute("message", "The Customer Id: " + id + " was not found in the database");
			return "404";
		}
	}

	@RequestMapping("customer/edit/{id}")
	public String edit(@PathVariable Integer id, Model model){
		model.addAttribute("customer", customerService.getCustomerById(id));
		return "customerform";
	}

	@RequestMapping("customer/new")
	public String newCustomer(Model model){
		model.addAttribute("customer", new Customer());
		return "customerform";
	}

	@RequestMapping(value = "customer", method = RequestMethod.POST)
	public String save(Customer customer){
		customerService.saveCustomer(customer);
		return "redirect:/customer/" + customer.getId();
	}

	@RequestMapping("customer/delete/{id}")
	public String delete(@PathVariable Integer id){
		customerService.deleteCustomer(id);
		return "redirect:/customers";
	}

	@RequestMapping(value = "customer/search", method = RequestMethod.POST)
	public String search(@RequestParam String phoneNum, Model model){
		model.addAttribute("customers", customerService.findByPhoneNum(phoneNum));
		return "customers";
	}
}
