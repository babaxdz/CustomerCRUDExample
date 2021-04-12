package tel.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tel.example.model.Customer;
import tel.example.repository.CustomerRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	// Retrieve Operation
	
	@GetMapping("/get/all")
	public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String name) {
	  try {
	    List<Customer> customers = new ArrayList<Customer>();

	    if (name == null)
	      customerRepository.findAll().forEach(customers::add);
	    else
	      customerRepository.findByName(name).forEach(customers::add);
	    if (customers.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    return new ResponseEntity<>(customers, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	// Retrieve Operation
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Integer id) {
		try {
			Optional<Customer> customerData = customerRepository.findById(id);

			if (customerData.isPresent()) {
				return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Create Operation
	@PostMapping("/add")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
	  try {
	    Customer _customer = customerRepository.save(new Customer(customer.getName(), customer.getAddress()));
	    return new ResponseEntity<>(_customer, HttpStatus.CREATED);
	  } catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	//Update Operation
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer Customer) {
	  Optional<Customer> customerData = customerRepository.findById(id);

	  if (customerData.isPresent()) {
	    Customer _customer = customerData.get();
	    _customer.setName(Customer.getName());
	    _customer.setAddress(Customer.getAddress());
	    return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}
	
	//Delete Operation
	
	@DeleteMapping("/deleteAll")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
	  try {
	    customerRepository.deleteAll();
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	// Delete Operation
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Integer id) {
	  try {
	    customerRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

}
