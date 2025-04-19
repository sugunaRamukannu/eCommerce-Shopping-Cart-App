package sg.nus.iss.service.ecommerceapp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.OtpRequestDto;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;
import sg.nus.iss.service.ecommerceapp.service.OtpService;

@Controller
public class PasswordResetController {

	@Autowired
	private OtpService otpService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerService customerService;

	private final Map<String, String> otpStorage = new HashMap<>();

	@GetMapping("/forgot-password")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("otpRequest", new OtpRequestDto());
		return "forgot-password";
	}

	@PostMapping("/send-otp")
	public String sendOtp(@ModelAttribute OtpRequestDto otpRequest, Model model) {
		Optional<Customer> optionalCustomer = customerService.findBymobilePhoneNumber(otpRequest.getMobileNumber());

		if (optionalCustomer.isPresent()) {
			String otp = String.valueOf(new Random().nextInt(999999));
			otpService.sendOtp(otpRequest.getMobileNumber(), otp);
			otpStorage.put(otpRequest.getMobileNumber(), otp);

			model.addAttribute("otpSent", true);

		}else {
			model.addAttribute("usererror", "Mobile number not registered");
		}
		model.addAttribute("otpRequest", otpRequest);
		
		return "forgot-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@ModelAttribute OtpRequestDto otpRequest, Model model,
			RedirectAttributes redirectAttributes) {
		// Retrieve OTP from the storage
		String savedOtp = otpStorage.get(otpRequest.getMobileNumber());

		// Check if the OTP is valid
		if (savedOtp != null && savedOtp.equals(otpRequest.getOtp())) {
			// Retrieve the customer by mobile number
			Optional<Customer> optionalCustomer = customerService.findBymobilePhoneNumber(otpRequest.getMobileNumber());

			if (optionalCustomer.isPresent()) {
				Customer customer = optionalCustomer.get();
				String encoded = passwordEncoder.encode(otpRequest.getNewPassword());
				customer.setPassword(encoded);
				customerService.saveCustomer(customer);
				model.addAttribute("otpRequest", otpRequest);
				redirectAttributes.addFlashAttribute("successMessage", "Password successfully updated");
			}
		} else {
			// Handle invalid OTP
			model.addAttribute("otpRequest", otpRequest);
			model.addAttribute("otpSent", true);
			model.addAttribute("otperror", "Invalid OTP");
			return "forgot-password";
		}
		// Redirect to the login page
		return "redirect:/login";
	}

}