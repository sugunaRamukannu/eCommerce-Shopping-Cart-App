package sg.nus.iss.service.ecommerceapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.nus.iss.service.ecommerceapp.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Optional <Customer> findBymobilePhoneNumber(String mobilePhoneNumber);
	

}
