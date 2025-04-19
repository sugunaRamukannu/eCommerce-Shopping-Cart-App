package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;

public interface ProductService {

	List<Product> getAllProducts();

	public Product saveProducts(Product product);

	public List<Product> searchProductByName(String name);

	public List<Product> searchProductByCategory(String category);

	public List<Product> getProductsByCategory(int categoryId);

	public boolean deleteProduct(int id);

	Optional<Product> findProduct(int id);

	Page<Product> findAllProducts(Pageable pageable);

	Product editProduct(Product existingProduct);

	public ProductCategory saveCategory(ProductCategory category);

}
