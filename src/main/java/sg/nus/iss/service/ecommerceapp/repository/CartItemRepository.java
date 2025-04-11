package sg.nus.iss.service.ecommerceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	List<CartItem> findByCheckedOut(boolean checkedOut);
}
