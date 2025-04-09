package sg.nus.iss.service.ecommerceapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Optional<Customer> findByPhoneNumber(String phoneNumber);
	boolean existsByPhoneNumber(String phoneNumber);
	
	@Query("SELECT d FROM DeliveryAddress d WHERE d.customer.id = :id")
	List<DeliveryAddress> findDeliveryAddressesByCustomer(@Param("id") int customerId);
}
