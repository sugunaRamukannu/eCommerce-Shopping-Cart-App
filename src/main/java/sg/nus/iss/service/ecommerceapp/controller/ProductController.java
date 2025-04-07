package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	@GetMapping("/")
	public String displayProducts() {
		return "index";
	}
	
	@GetMapping("/cart")
	public String displayProductss() {
		return "cart";
	}
	
	@GetMapping("/account/create")
	public String createAccount() {
		return "create-account";
	}

	@GetMapping("/products")
	public String products() {
		return "products";
	}
}
