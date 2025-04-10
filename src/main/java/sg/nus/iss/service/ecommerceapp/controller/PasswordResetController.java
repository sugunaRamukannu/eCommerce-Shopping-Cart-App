package sg.nus.iss.service.ecommerceapp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.OtpRequestDto;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;
import sg.nus.iss.service.ecommerceapp.service.OtpService;

@Controller
public class PasswordResetController {

	@Autowired
    private  OtpService otpService;
	
	@Autowired
	private CustomerService customerService;
	
    
    private final Map<String, String> otpStorage = new HashMap<>();

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
    	System.out.println("asd");
        model.addAttribute("otpRequest", new OtpRequestDto());
        return "forgot-password";
    }

    @PostMapping("/send-otp")
    public String sendOtp(@ModelAttribute OtpRequestDto otpRequest, Model model) {
    	System.out.println("mobile"+otpRequest.getMobileNumber());
        String otp = String.valueOf(new Random().nextInt(999999));
    	System.out.println("otp"+otp);
        otpService.sendOtp(otpRequest.getMobileNumber(), otp);
        otpStorage.put(otpRequest.getMobileNumber(), otp);

        model.addAttribute("otpRequest", otpRequest);
        model.addAttribute("otpSent", true);
        return "forgot-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute OtpRequestDto otpRequest, Model model,Customer customer) {
        String savedOtp = otpStorage.get(otpRequest.getMobileNumber());
        System.out.println("savedOtp"+savedOtp);
        if (savedOtp != null && savedOtp.equals(otpRequest.getOtp())) {
        	 System.out.println("savedOtpij"+savedOtp);
             customer.setPassword(otpRequest.getNewPassword());
        	 customerService.saveCustomer(customer);
        	 model.addAttribute("otpRequest",otpRequest);
            model.addAttribute("message", "Password successfully updated.");
        } else {
            model.addAttribute("error", "Invalid OTP.");
        }
        return "redirect:/login";
    }
}