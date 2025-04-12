package sg.nus.iss.service.ecommerceapp.service;


import java.util.Optional;


import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerRepository customerRepo;
	
	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.customerRepo=customerRepo;
	}
	
	
	@Override
	public boolean checkMobileExists(String mobilePhoneNumber) {
		 System.out.println("phonenumber"+mobilePhoneNumber);
		 System.out.println("phonenumbersa"+customerRepo.findByMobilePhoneNumber(mobilePhoneNumber).isPresent());
//		Optional<Customer> user= customerRepo.findByPhoneNumber(customer.getMobilePhoneNumber());
		//instead of writing code that checks for null values using if statements, you can use Optional methods to handle null values more
		return customerRepo.findByMobilePhoneNumber(mobilePhoneNumber).isPresent();
		
	
	};
	
	@Override
	public Customer saveCustomer(Customer customer) {
		System.out.println("dsgfjsd");
		return customerRepo.save(customer);
	}


	@Override
	public Optional<Customer> findBymobilePhoneNumber(String username) {
		// TODO Auto-generated method stub
		return customerRepo.findByMobilePhoneNumber(username);
	}



}
