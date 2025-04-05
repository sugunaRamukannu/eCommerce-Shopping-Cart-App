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
}
