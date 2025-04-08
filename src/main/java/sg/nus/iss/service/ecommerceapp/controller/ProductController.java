package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
	@GetMapping("/")
	public String displayProducts() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/login/otp")
	public String enterOTP() {
		return "otp";
	}
	
	@GetMapping("/account/create")
	public String createAccount() {
		return "create-account";
	}

	@GetMapping("/products")
	public String products() {
		return "products";
	}
	
	@GetMapping("/categories")
	public String categories() {
		return "categories";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
}
