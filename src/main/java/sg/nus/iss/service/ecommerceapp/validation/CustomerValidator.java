package sg.nus.iss.service.ecommerceapp.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.iss.service.ecommerceapp.model.Customer;

@Component
public class CustomerValidator implements Validator{
	@Override
	 public boolean supports(Class<?> clazz) {
	 return Customer.class.isAssignableFrom(clazz);
	 }
	
	@Override
	 public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobilePhoneNumber",
				 "error.mobilePhoneNumber", "Mobile Number is required.");
				 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				 "error.password", "Please set password");
				 Customer course = (Customer) obj;  
//				if ((course.getStartDate() != null && course.getEndDate() != null) &&
//				 (course.getStartDate().compareTo(course.getEndDate()) > 0)) {
//				 errors.rejectValue("endDate", "error.dates", 
//				"End Date must be later than Start Date");
//				 if()
		 }
		 }


