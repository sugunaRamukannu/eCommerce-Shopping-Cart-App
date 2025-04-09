package sg.nus.iss.service.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
