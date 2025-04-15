package sg.nus.iss.service.ecommerceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByCustomer (Customer customer);

}
