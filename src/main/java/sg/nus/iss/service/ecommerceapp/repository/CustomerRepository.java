package sg.nus.iss.service.ecommerceapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Optional<Customer> findByPhoneNumber(String phoneNumber);
	boolean existsByPhoneNumber(String phoneNumber);
}
