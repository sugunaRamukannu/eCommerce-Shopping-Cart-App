package sg.nus.iss.service.ecommerceapp.repository;


import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Optional <Customer> findBymobilePhoneNumber(String mobilePhoneNumber);


//	boolean existsByPhoneNumber(String mobilePhoneNumber);
	
	@Query("SELECT d FROM DeliveryAddress d WHERE d.customer.id = :id")
	List<DeliveryAddress> findDeliveryAddressesByCustomer(@Param("id") int customerId);

}
