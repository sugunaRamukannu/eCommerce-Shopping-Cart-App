package sg.nus.iss.service.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {

}
