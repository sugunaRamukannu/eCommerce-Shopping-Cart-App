package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Author(s): Lee Yi Cheng, Melvin

@Controller
public class TermsController {
	
	@GetMapping("/terms")
	public String termsPage() {
		return "terms";
	}
	
}
