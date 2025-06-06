package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sg.nus.iss.service.ecommerceapp.exception.OrderNotFoundException;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;
import sg.nus.iss.service.ecommerceapp.service.OrderService;

//Author(s): Yu Yaotian, Li Zhuoxuan, Pang Siang Lian, Irene Chan Oei Lin

@Controller
public class PurchaseHistoryController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	//Author(s): Ramukannu Suguna
	@GetMapping("/purchase-history")
	public String viewPurchaseHistory(Model model, Authentication authentication) {
		String mobilePhoneNumber = authentication.getName();
		Optional<Customer> optCustomer = customerService.findBymobilePhoneNumber(mobilePhoneNumber);
		if (optCustomer.isPresent()) {
			Customer customer = optCustomer.get();
			List<Order> orders = orderService.findByCustomer(customer);
			model.addAttribute("orders", orders);
		} 
		return "purchase-history";
	}

	@GetMapping("/order-detail/{id}")
	public String viewOrderDetail(@PathVariable("id") int id, Model model) {
		Optional<Order> optionalOrder = orderService.findById(id);
		if (optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			model.addAttribute("order", order);
			return "order-detail";
		} else {
			throw new OrderNotFoundException("Order with ID " + id + " not found.");
		}
	}

}
