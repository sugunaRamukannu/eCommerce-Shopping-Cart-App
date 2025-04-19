package sg.nus.iss.service.ecommerceapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.repository.ProductCategoryRepository;
import sg.nus.iss.service.ecommerceapp.repository.ProductRepository;
import sg.nus.iss.service.ecommerceapp.service.ProductService;

//Author(s): Yu Yaotian, Li Zhuoxuan, Pang Siang Lian, Irene Chan Oei Lin

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(int categoryId) {
		return productRepository.findByProductCategoryId(categoryId);
	}

	@Override
	public List<Product> searchProductByName(String name) {
		return productRepository.findByProductName(name);
	}

	@Override
	public List<Product> searchProductByCategory(String category) {
		return productRepository.findByProductCategory(category);
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

	@Override
	public Page<Product> findAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Product saveProducts(Product product) {
		return productRepository.save(product);
	}

	@Override
	public ProductCategory saveCategory(ProductCategory category) {
		return productCategoryRepository.save(category);
	}

}
