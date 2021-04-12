package tel.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import tel.example.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {
	
	public List<Customer> findByName(String name);

}
