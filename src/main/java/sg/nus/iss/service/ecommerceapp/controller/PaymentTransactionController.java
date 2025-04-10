package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentTransactionController {
	@GetMapping("/payment")
	public String payment() {
		return "payment";
	}
	
	@GetMapping("/history")
	public String history() {
		return "purchase-history";
	}
}
