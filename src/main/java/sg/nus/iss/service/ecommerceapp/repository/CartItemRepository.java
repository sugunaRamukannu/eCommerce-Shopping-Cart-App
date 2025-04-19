package sg.nus.iss.service.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.nus.iss.service.ecommerceapp.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
