package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.LoginDto;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;

@Controller
@Slf4j
@AllArgsConstructor
public class LoginController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerService customerService;
	
	
	 @GetMapping("/login")
	    public String loginPage(Model model, @RequestParam(required = false) String error) {
	        model.addAttribute("logindto", new LoginDto());
	        model.addAttribute("showPassword", false);
	        return "login";
	    }

	    @PostMapping("/login-check")
	    public String checkMobileAndRedirect(@ModelAttribute LoginDto logindto, Model model, RedirectAttributes redirectAttributes) {
	        boolean userExists = customerService.checkMobileExists(logindto.getUsername());
           System.out.println("userexits"+userExists);
	        if (userExists) {
	            model.addAttribute("logindto", logindto );
	            model.addAttribute("showPassword", true);
	            return "login"; // Reload login page with password field
	        } else {
	            redirectAttributes.addFlashAttribute("mobilePhoneNumber", logindto.getUsername());
	            return "redirect:/createAccount";
	        }
	    }
	    
	  
	    @GetMapping("/home")
	    public String homePage() {
	        return "home";
	    }

	
	@PostMapping("/submit-password")
	public String savePassword(Model model) {
		  System.out.println("userexits");
		model.addAttribute("logindto", new LoginDto());
		return "enter-password";
	}
	
	@GetMapping("/createAccount")
	public String createAccount(Model model) {
		model.addAttribute("customer", new Customer());
		return "create-account";
	}
	@PostMapping("/register")
	public String register(@ModelAttribute Customer customer, Model model) {
		String encoded = passwordEncoder.encode(customer.getPassword());
	    customer.setPassword(encoded);
	    customerService.saveCustomer(customer);

	 boolean isUserExists = true; // Set your condition here
	 System.out.println("isValidCustomersregist"+isUserExists);
	 model.addAttribute("showPassword",isUserExists);
	return "/login";
	}
}
