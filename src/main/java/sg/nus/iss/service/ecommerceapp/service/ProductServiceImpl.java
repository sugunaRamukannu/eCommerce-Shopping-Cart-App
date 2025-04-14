package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> listAllProducts() {

		return productRepository.findAll();
	}

	@Override
	@Transactional
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	@Override
	@Transactional
	public List<Product> findFeaturedProducts() {
		List<Product> allProducts = productRepository.findAll();
		return allProducts.stream().limit(6).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean saveProduct(Product product) {
		if (productRepository.save(product) != null)
			return true;
		else
			return false;
	}

	@Override
	@Transactional
	public Product saveProducts(Product product) {
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public List<Product> SearchProductByName(String name) {
		return productRepository.SearchProductByName(name);
	}

	@Override
	@Transactional
	public List<Product> SearchProductByCategory(String category) {
		return productRepository.SearchProductByCategory(category);
	}

	@Override
	@Transactional
	public List<Product> getProductsByCategory(int categoryId) {
		return productRepository.findByCategoryId(categoryId);
	}

	@Override
	public boolean deleteProduct(int id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
		}
		return false;
	}

	@Override
	public Optional<Product> findProduct(int id) {
		return productRepository.findById(id);
	}

	@Override
	public Product editProduct(Product existingProduct) {
		return productRepository.save(existingProduct);
	}
}
