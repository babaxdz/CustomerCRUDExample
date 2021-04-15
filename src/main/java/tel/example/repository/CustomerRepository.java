package tel.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tel.example.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {
	
}
