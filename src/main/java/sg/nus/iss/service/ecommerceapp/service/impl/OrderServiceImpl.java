package sg.nus.iss.service.ecommerceapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.repository.OrderRepository;
import sg.nus.iss.service.ecommerceapp.service.OrderService;

//Author(s): Ramukannu Suguna, Andy Teow Rui Qing

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepo;

	@Override
	public List<Order> findByCustomer(Customer customer) {
		return orderRepo.findByCustomer(customer);
	}

	@Override
	public Optional<Order> findById(int id) {
		return orderRepo.findById(id);
	}

}
