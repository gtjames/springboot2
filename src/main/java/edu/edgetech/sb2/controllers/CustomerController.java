package edu.edgetech.sb2.controllers;

import edu.edgetech.sb2.models.Customer;
import edu.edgetech.sb2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	//  RequestMethod.GET is the default. It is optional
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("customers", customerService.listAllCustomers());
		return "customers";
	}

	@RequestMapping("/{id}")
	public String read(@PathVariable Integer id, Model model){
		//@RequestParam String thing,
		Customer cust = customerService.getCustomerById(id);
		if ( cust != null ) {
			model.addAttribute("customer", cust);
			return "customerDetails";
		}
		else {
			model.addAttribute("message", "The Customer Id: " + id + " was not found in the database");
			return "404";
		}
	}

	@RequestMapping("/edit/{id}")
	public String update(@PathVariable Integer id, Model model){
		model.addAttribute("customer", customerService.getCustomerById(id));
		return "customerEdit";
	}

	@RequestMapping("/new")
	public String newCustomer(Model model){
		model.addAttribute("customer", new Customer());
		return "customerEdit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Customer customer){
		customerService.saveCustomer(customer);
		return "redirect:/customer/list";
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id){
		customerService.deleteCustomer(id);
		return "redirect:/customer/list";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam String phoneNum, Model model){
		model.addAttribute("customers", customerService.findByPhoneNum(phoneNum));
		return "customers";
	}

	@RequestMapping("/tree/{levels}")
	public String tree(@PathVariable Integer levels, Model model){
		String row = new String(new char[levels-1]).replace("\0", " ") +
	                 new String(new char[levels*2-1]).replace("\0", "*");
		for ( int i = 1; i <= levels; i++ ) {
			System.out.println(row.substring(i-1, levels-1+2*i-1) );
		}
		model.addAttribute("customers", customerService.listAllCustomers());
		return "customers";
	}
}
