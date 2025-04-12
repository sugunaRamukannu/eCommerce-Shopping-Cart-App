package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import sg.nus.iss.service.ecommerceapp.repository.ProductRepository;
import sg.nus.iss.service.ecommerceapp.service.ProductService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class ProductRestController {
	
	@Autowired
	public  ProductService productService;
	
	@GetMapping("/products")
	    public List<ProductAdminDto> getAllProductsForAdmin() {
	        // Fetch all products from the database
	        List<Product> products = productService.findAllProducts();
             //DTO -- data transfer object
	        // Stream through the list of products and map them to ProductAdminDTO
	        return products.stream()
	                .map((Product product) -> {
	                    ProductAdminDto dto = new ProductAdminDto();
	                    dto.setProductId((long) product.getId());
	                    dto.setProductName(product.getName());
	                    dto.setPrice(product.getPrice());
	                    dto.setLabels(product.getLabels());
	               

	                    if (product.getProductCategory() != null) {
	                        dto.setCategoryId( product.getProductCategory().getId());
	                        dto.setCategoryName(product.getProductCategory().getCategory());
	                    }

	                    return dto;
	                })
	                .collect(Collectors.toList()); // Collect into a list of ProductAdminDTO
	    }
	
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductAdminDto> getProductById(@PathVariable int id) {
	    Optional<Product> selectedProduct = productService.findProduct(id);
	    System.out.println("ad");	

	    if (selectedProduct.isPresent()) {
	        Product product = selectedProduct.get();
	        ProductAdminDto dto = new ProductAdminDto();
	        dto.setProductId((long) product.getId());
	        dto.setProductName(product.getName());
	        dto.setPrice(product.getPrice());
	        dto.setLabels(product.getLabels());
//	        dto.setStock(product.getCartItems() != null ? product.getCartItems().size() : 0);

	        if (product.getProductCategory() != null) {
	            dto.setCategoryId( product.getProductCategory().getId());
	            dto.setCategoryName(product.getProductCategory().getCategory());
	        }

	        return new ResponseEntity<ProductAdminDto>(dto,HttpStatus.CREATED);
	    } else {
	        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	    }
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody ProductAdminDto productDto) {
		try {
			 Product product = new Product();
			    product.setName(productDto.getProductName());
			    product.setPrice(productDto.getPrice());
			    product.setLabels(productDto.getLabels());

			    ProductCategory category = new ProductCategory();
			    category.setId(productDto.getCategoryId());
			    category.setCategory(productDto.getCategoryName());

			    product.setProductCategory(category);
		Product newProduct=productService.saveProducts(product);
		System.out.println(product.getName());
		System.out.println(product.getPrice());
		System.out.println(product.getLabels());
		
		System.out.println(newProduct.getName());
		System.out.println(newProduct.getPrice());
		System.out.println(newProduct.getLabels());
		 return new ResponseEntity<Product>(newProduct,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@PutMapping("/Products/{id}")
	public ResponseEntity<Product> editProduct(@PathVariable int id, @RequestBody Product inProduct){
		Optional<Product> editProd = productService.findProduct(id);
		if(editProd.isPresent()) {
			Product existingProduct=editProd.get();
		
			existingProduct.setId(inProduct.getId());
			existingProduct.setName(inProduct.getName());
			existingProduct.setPrice(inProduct.getPrice());
			
			existingProduct.setProductCategory(inProduct.getProductCategory());
			
			Product updatedProduct=productService.editProduct(existingProduct);
			 return new ResponseEntity<Product>(updatedProduct,HttpStatus.CREATED); 
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			
		}
		
	 
	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable int id) {
		try {
		System.out.println("delete");
	     productService.deleteProduct(id);
	    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT); 
		}
	   catch(Exception e) {
		   return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED); 
	   }
		
	}
	

}
