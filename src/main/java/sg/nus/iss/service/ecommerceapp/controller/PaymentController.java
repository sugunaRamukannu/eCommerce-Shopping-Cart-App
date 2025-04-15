package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderSummary;
import sg.nus.iss.service.ecommerceapp.service.PaymentService;

@Controller
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	//confirm purchase after reviewing checkout page
	//redirected to payment page
	//generate orderId
	//show summary of purchase in a small table (orderId, total price, products, quantity)
	//key in delivery address
	//select delivery slot
	//key in billing address
	//key in payment details
	//redirect to order placed page
	//update status in database (pending payment, paid, cancelled)
	@GetMapping("/order/success")
	public String showOrderSuccessPage(@RequestParam String orderId, Model model) {
		
		OrderSummary summary = paymentService.getOrderSummary(orderId);
		
		model.addAttribute("orderItems", summary.getOrderItems());
		model.addAttribute("orderSummary", summary);
		
		return "order-success";
	}
	
	@PostMapping("/cart/checkout/payment")
	public String confirmCheckout(@RequestParam String deliveryAddress, @RequestParam String paymentMethod, Authentication authentication) {
		
		//TODO Add validation check for inputs by binding @ModelAttributes 
		String mobilePhoneNumber = authentication.getName();
		
		Order order = paymentService.confirmOrder(deliveryAddress, paymentMethod, mobilePhoneNumber);
		return "redirect:/order/success?orderId=" + order.getOrderIdString();
	}
}
