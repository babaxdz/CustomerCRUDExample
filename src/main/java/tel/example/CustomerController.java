package tel.example;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping("/")
	public List<Customer> list() {
	    return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> get(@PathVariable Integer id) {
	    try {
	        Customer Customer = service.get(id);
	        return new ResponseEntity<Customer>(Customer, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	@PostMapping("/addCustomer")
	public void add(@RequestBody Customer Customer) {
	    service.save(Customer);
	}
	
	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<?> update(@RequestBody Customer Customer, @PathVariable Integer id) {
	    try {
	        Customer existCustomer = service.get(id);
	        service.save(Customer);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	@DeleteMapping("/deleteCustomer/{id}")
	public void delete(@PathVariable Integer id) {
	    service.delete(id);
	}

}
