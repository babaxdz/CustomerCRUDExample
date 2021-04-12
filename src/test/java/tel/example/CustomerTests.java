package tel.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import tel.example.model.Customer;
import tel.example.repository.CustomerRepository;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class CustomerTests {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateCustomer() {
		
		Customer customer = new Customer("Smith", "NewYork");
		Customer savedCustomer = customerRepository.save(customer);
		assertNotNull(savedCustomer);
		
	}
	
	@Test
	@Order(2)
	public void testFindCustomerByNameExist() {
		String name = "Smith";
		List<Customer> customer = customerRepository.findByName(name);
		assertThat(((Customer) customer).getName()).isEqualTo(name);
	}
	
	@Test
	@Order(3)
	public void testFindCustomerByNameNotExist() {
		String name = "Smith11";
		List<Customer> customer = customerRepository.findByName(name);
		assertNull(customer);
	}

	@Test
	@Rollback(false)
	@Order(4)
	public void testUpdateCustomer() {
		String customerName = "Andrew";
		Customer customer = new Customer(customerName, "NewYork");
		customer.setId(2);
		
		customerRepository.save(customer);
		
		List<Customer> updatedCustomer = customerRepository.findByName(customerName);
		assertThat(((Customer) updatedCustomer).getName()).isEqualTo(customerName);
	}
	
	@Test
	@Order(5)
	public void testListCustomers() {
		List<Customer> customers = (List<Customer>) customerRepository.findAll();
		
		for(Customer customer : customers) {
			System.out.println(customer);
		}
		assertThat(customers).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testDeleteCustomerById() {
		Integer id = 2;
		
		boolean isExistBeforeDelete = customerRepository.findById(id).isPresent();
		
		customerRepository.deleteById(id);
		
		boolean isExistAftereDelete = customerRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		assertFalse(isExistAftereDelete);
		
	}
}
