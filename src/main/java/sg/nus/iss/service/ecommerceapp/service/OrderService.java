package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;
import java.util.Optional;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;

public interface OrderService {
	
	List<Order> findByCustomer (Customer customer);
	
	Optional<Order> findById (int id);
}
