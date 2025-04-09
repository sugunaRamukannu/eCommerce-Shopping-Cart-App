package sg.nus.iss.service.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.service.ecommerceapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
