package sg.nus.iss.service.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.PaymentTransaction;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {

}
