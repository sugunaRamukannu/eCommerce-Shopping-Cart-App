package sg.nus.iss.service.ecommerceapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderItem;
import sg.nus.iss.service.ecommerceapp.repository.OrderItemRepository;
import sg.nus.iss.service.ecommerceapp.service.OrderItemService;

//Author(s): Ramukannu Suguna, Andy Teow Rui Qing

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Override
	public List<OrderItem> findByOrder(Order order) {
		return orderItemRepo.findByOrder(order);
	}
	
}
