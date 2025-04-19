package sg.nus.iss.service.ecommerceapp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.repository.CustomerRepository;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public boolean checkMobileExists(String mobilePhoneNumber) {
		return customerRepo.findByMobilePhoneNumber(mobilePhoneNumber).isPresent();
	};

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public Optional<Customer> findBymobilePhoneNumber(String username) {
		return customerRepo.findByMobilePhoneNumber(username);
	}

}
