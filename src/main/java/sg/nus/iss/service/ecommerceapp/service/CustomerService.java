package sg.nus.iss.service.ecommerceapp.service;

import java.util.Optional;


import sg.nus.iss.service.ecommerceapp.model.Customer;

public interface CustomerService {
	
	public boolean checkMobileExists(String mobilePhoneNumber);
	
	public Customer saveCustomer(Customer customer);
	
	public Optional<Customer> findBymobilePhoneNumber(String username);
	


}
