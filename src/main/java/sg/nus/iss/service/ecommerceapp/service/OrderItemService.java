package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;

import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderItem;

public interface OrderItemService {
	
	List<OrderItem> findByOrder(Order order);

}
