package tel.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import tel.example.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllCustomers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/Customers",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetCustomerById() {
		Customer customer = restTemplate.getForObject(getRootUrl() + "/Customers/1", Customer.class);
		System.out.println(customer.getFirstName());
		assertNotNull(customer);
	}

	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setEmailId("admin@gmail.com");
		customer.setFirstName("admin");
		customer.setLastName("admin");

		ResponseEntity<Customer> postResponse = restTemplate.postForEntity(getRootUrl() + "/Customers", customer, Customer.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateCustomer() {
		int id = 1;
		Customer customer = restTemplate.getForObject(getRootUrl() + "/Customers/" + id, Customer.class);
		customer.setFirstName("admin1");
		customer.setLastName("admin2");

		restTemplate.put(getRootUrl() + "/Customers/" + id, customer);

		Customer updatedCustomer = restTemplate.getForObject(getRootUrl() + "/customers/" + id, Customer.class);
		assertNotNull(updatedCustomer);
	}

	@Test
	public void testDeleteCustomer() {
		int id = 2;
		Customer customer = restTemplate.getForObject(getRootUrl() + "/Customers/" + id, Customer.class);
		assertNotNull(customer);

		restTemplate.delete(getRootUrl() + "/Customers/" + id);

		try {
			customer = restTemplate.getForObject(getRootUrl() + "/customers/" + id, Customer.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
