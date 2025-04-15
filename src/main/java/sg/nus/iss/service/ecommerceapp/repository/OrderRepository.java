package sg.nus.iss.service.ecommerceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByCustomer (Customer customer);
	
	@Query("SELECT o FROM Order o WHERE o.orderIdString = :orderId")
	Order findByOrderId(@Param("orderId") String orderId);
}
