package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;
//import java.util.List;
import java.util.Optional;

//import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ProductAdminDto;
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.model.ProductCategoryDto;
import sg.nus.iss.service.ecommerceapp.service.ProductCategoryService;
import sg.nus.iss.service.ecommerceapp.service.ProductService;

//Author(s): Ramukannu Suguna

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ProductRestController {

	@Autowired
	public ProductService productService;

	@Autowired
	public ProductCategoryService productCategoryService;

	// since am showing the product details by combining two objects, i have another
	// one DTO called ProductAdminDto
	@GetMapping("/products")
	public Page<ProductAdminDto> getAllProductsForAdmin(Pageable pageable) {
		Page<Product> productsPage = productService.findAllProducts(pageable);

//data is passed from product entity to dto entity
		return productsPage.map(product -> {
			ProductAdminDto dto = new ProductAdminDto();
			dto.setProductId((long) product.getId());

			dto.setProductName(product.getProductName());

			dto.setPrice(product.getPrice());
			dto.setLabels(product.getLabels());

			if (product.getProductCategory() != null) {
				dto.setCategoryName(product.getProductCategory().getCategory());
			}
			return dto;
		});
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductAdminDto> getProductById(@PathVariable int id) {
		Optional<Product> selectedProduct = productService.findProduct(id);
		if (selectedProduct.isPresent()) {
			Product product = selectedProduct.get();
			ProductAdminDto dto = new ProductAdminDto();
			dto.setProductId((long) product.getId());
			dto.setProductName(product.getProductName());
			dto.setPrice(product.getPrice());
			dto.setLabels(product.getLabels());

			if (product.getProductCategory() != null) {
				dto.setCategoryName(product.getProductCategory().getCategory());
			}

			return new ResponseEntity<ProductAdminDto>(dto, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody ProductAdminDto productDto) {
		try {
			ProductCategory category = new ProductCategory();
			category.setId(productDto.getCategoryId());
			category.setCategory(productDto.getCategoryName());
			productService.saveCategory(category);

			Product product = new Product();
			product.setProductName(productDto.getProductName());
			product.setPrice(productDto.getPrice());
			product.setLabels(productDto.getLabels());
			product.setProductCategory(category);

			Product newProduct = productService.saveProducts(product);
			return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	@GetMapping("/categories")
	public List<ProductCategoryDto> getAllCategories() {
		return productCategoryService.findCategories();
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> editProduct(@PathVariable int id, @RequestBody Product inProduct) {
		Optional<Product> editProd = productService.findProduct(id);
		System.out.println("editprod" + editProd.toString());
		if (editProd.isPresent()) {
			Product existingProduct = editProd.get();
			existingProduct.setProductName(inProduct.getProductName());
			existingProduct.setPrice(inProduct.getPrice());
			existingProduct.setLabels(inProduct.getLabels());
			existingProduct.setProductCategory(inProduct.getProductCategory());

			Product updatedProduct = productService.editProduct(existingProduct);
			return new ResponseEntity<Product>(updatedProduct, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable int id) {
		try {
			productService.deleteProduct(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
