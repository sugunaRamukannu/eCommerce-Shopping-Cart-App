package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Product;
<<<<<<< Updated upstream
import sg.nus.iss.service.ecommerceapp.model.ProductAdminDto;
=======
>>>>>>> Stashed changes
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;

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
	Page<Product> findAllProducts(Pageable pageable) ;

<<<<<<< Updated upstream
	public ProductCategory saveCategory(ProductCategory category);




=======
	ProductCategory saveCategory(ProductCategory category);
>>>>>>> Stashed changes

}
