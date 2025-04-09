package sg.nus.iss.service.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
