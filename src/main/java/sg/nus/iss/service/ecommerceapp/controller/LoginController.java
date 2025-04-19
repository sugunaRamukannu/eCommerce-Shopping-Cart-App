package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.LoginDto;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;

//Author(s): Ramukannu Suguna

@Controller
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
		if (error != null) {
			model.addAttribute("loginError", "Invalid password");
		}
		return "login";
	}

	@PostMapping("/login-check")
	public String checkMobileAndRedirect(@Valid @ModelAttribute("logindto") LoginDto logindto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("logindto", logindto);
			return "login";
		}

		boolean userExists = customerService.checkMobileExists(logindto.getUsername());
		if (userExists) {
			model.addAttribute("logindto", logindto);
			model.addAttribute("showPassword", true);
			return "login";
		} else {
			redirectAttributes.addFlashAttribute("mobilePhoneNumber", logindto.getUsername());
			return "redirect:/createAccount";
		}
	}

	@GetMapping("/createAccount")
	public String createAccount(@ModelAttribute("mobilePhoneNumber") String mobile, Model model) {
		Customer customer = new Customer();
		customer.setMobilePhoneNumber(mobile);
		model.addAttribute("customer", customer);
		return "create-account";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute Customer customer, BindingResult bindingResult, Model model) {
		boolean isMobileNumberExists = customerService.checkMobileExists(customer.getMobilePhoneNumber());
		if (bindingResult.hasErrors()|| isMobileNumberExists) {
			model.addAttribute("mobileExists", isMobileNumberExists);
			model.addAttribute("customer", customer);
			return "create-account";
		}
		
		String encoded = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encoded);
		customerService.saveCustomer(customer);
		model.addAttribute("logindto", new LoginDto());
		model.addAttribute("showPassword", true);
		return "/login";
	}

}
