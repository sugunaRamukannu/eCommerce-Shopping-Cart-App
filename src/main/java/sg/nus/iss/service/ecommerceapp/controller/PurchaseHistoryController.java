package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderItem;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;
import sg.nus.iss.service.ecommerceapp.service.OrderItemService;
import sg.nus.iss.service.ecommerceapp.service.OrderService;

@Controller
public class PurchaseHistoryController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	//@GetMapping("/purchase-history")
	//public String viewPurchaseHistory(HttpSession sessionObj, Model model) {
		//Customer customer = (Customer) sessionObj.getAttribute("loggedInCustomer");
	
		@GetMapping("/purchase-history")
		public String viewPurchaseHistory(Model model, Authentication authentication) {
			
		String mobilePhoneNumber = authentication.getName();
		
		Optional<Customer> optCustomer = customerService.findBymobilePhoneNumber(mobilePhoneNumber);
		
		if (optCustomer.isPresent()) {
			Customer customer = optCustomer.get();
			
			List<Order> orders = orderService.findByCustomer(customer);
			model.addAttribute("orders", orders);
		} else {
			return "redirect:/login";
		}
		
			return "purchase-history";
		}
		
		
		
		@GetMapping("/order-detail/{id}")
		public String viewOrderDetail(@PathVariable("id") int id, Model model) {
			
			Optional<Order> optionalOrder = orderService.findById(id);
			if (optionalOrder.isPresent()) {
				Order order = optionalOrder.get();
			List<OrderItem> orderItems =  orderItemService.findByOrder(order);
			model.addAttribute("orderItems", orderItems);
			model.addAttribute("order", order);
			return "order-detail";
			} else {
				return "order-detail";
			}
		}
		
}
