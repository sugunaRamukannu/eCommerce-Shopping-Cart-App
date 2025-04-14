package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Product;

@Service
public interface ProductService {

	List<Product> listAllProducts();

	List<Product> findAllProducts();

	List<Product> findFeaturedProducts();

	public boolean saveProduct(Product product);
	
	//added for the admin panel
	public Product saveProducts(Product product);

	public List<Product> SearchProductByName(String name);

	public List<Product> SearchProductByCategory(String category);

	public List<Product> getProductsByCategory(int categoryId);

	public boolean deleteProduct(int id);

	Optional<Product> findProduct(int id);

	Product editProduct(Product existingProduct);

}
