package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Product;

@Service
public interface ProductService {

	List<Product> listAllProducts();

}
