package sg.nus.iss.service.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>{

}
