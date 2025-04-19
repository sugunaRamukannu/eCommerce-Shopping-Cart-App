package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.service.ecommerceapp.exception.CustomerNotFoundException;
import sg.nus.iss.service.ecommerceapp.exception.OrderNotFoundException;
import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderSummary;
import sg.nus.iss.service.ecommerceapp.service.PaymentService;

@Controller
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/order/success")
	public String showOrderSuccessPage(@RequestParam String orderId, Model model) {

		if (orderId == null || orderId.trim().isEmpty()) {
			throw new IllegalArgumentException("Order ID must be provided.");
		}
		OrderSummary summary = paymentService.getOrderSummary(orderId);

		if (summary == null) {
			throw new OrderNotFoundException("No order found for ID: " + orderId);
		}

		model.addAttribute("orderItems", summary.getOrderItems());
		model.addAttribute("orderSummary", summary);

		return "order-success";
	}

	@PostMapping("/cart/checkout/payment")
	public String confirmCheckout(@RequestParam String deliveryAddress, @RequestParam String paymentMethod,
			Authentication authentication) {

		if (deliveryAddress == null || deliveryAddress.isEmpty() || paymentMethod == null || paymentMethod.isEmpty()) {
			throw new IllegalArgumentException("Delivery address and payment method must not be empty");
		}

		String mobilePhoneNumber = authentication.getName();
		if (mobilePhoneNumber == null || mobilePhoneNumber.isEmpty()) {
			throw new CustomerNotFoundException("Invalid customer: mobile number not found in authentication.");
		}

		Order order = paymentService.confirmOrder(deliveryAddress, paymentMethod, mobilePhoneNumber);
		return "redirect:/order/success?orderId=" + order.getOrderIdString();
	}

}
