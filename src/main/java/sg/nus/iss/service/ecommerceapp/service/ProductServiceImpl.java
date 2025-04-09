package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> listAllProducts() {
		
		return productRepository.findAll();
	}


}
