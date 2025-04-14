package sg.nus.iss.service.ecommerceapp.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.repository.ProductCategoryRepository;
import sg.nus.iss.service.ecommerceapp.service.ProductService;
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@GetMapping("/")
    public String homePage(Model model) {
    	
		List<Product> products = productService.findAllProducts();
    	List<ProductCategory> categories = productCategoryRepository.findAll();   	
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index"; 
    }
	

	@GetMapping("/products")
    public String getProducts(
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) String filter,
        Model model) {
        	
        // List all product method
        List<Product> products = productService.findAllProducts(); // assuming productService fetches the products
        model.addAttribute("products", products);
        
        // Sort logic
        if (sort != null) {
            switch (sort) {
            case "priceLowHigh" -> products.sort(Comparator.comparing(Product::getPrice));
            case "priceHighLow" -> products.sort(Comparator.comparing(Product::getPrice).reversed());
            case "nameAZ" -> products.sort(Comparator.comparing(Product::getName));
            case "nameZA" -> products.sort(Comparator.comparing(Product::getName).reversed());
            }
        }
        
        return "products"; // Thymeleaf template name
    }
	
	@GetMapping("/search")
	public String search(
	    @RequestParam("keyword") String keyword,
	    @RequestParam("searchtype") String searchType,
	    Model model) {

	    if ("name".equalsIgnoreCase(searchType)) {
	        model.addAttribute("products", productService.SearchProductByName(keyword));
	    } else if ("category".equalsIgnoreCase(searchType)) {
	        model.addAttribute("products", productService.SearchProductByCategory(keyword));
	    } else {
	        return "error";
	    }
	    return "products"; 
	}
	
	@PostMapping("/add-to-cart/{productId}")
	public String addToCart(@PathVariable int productId, Authentication authentication) {
		
		String mobilePhoneNumber = authentication.getName();
		
		shoppingCartService.addProductToCart(productId, mobilePhoneNumber);
		System.out.println("PRODUCT ADDED");
		
		return "redirect:/";
	}
	
	@GetMapping("/cart/summary")
	@ResponseBody
	public ResponseEntity<CartSummary> getCartSummary(Authentication authentication) {
		
		String mobilePhoneNumber = authentication.getName();
		
	    CartSummary summary = shoppingCartService.getCartSummary(mobilePhoneNumber);
	    return ResponseEntity.ok(summary);
	}
	
//	@PostMapping("/add-to-cart/{productId}")
//	@ResponseBody
//	public ResponseEntity<Map<String, Object>> addToCart(@PathVariable Integer productId) {
//		shoppingCartService.addProductToCart(productId);
//		
//		// Get updated cart summary to send back to the frontend
//		CartSummary cartSummary = shoppingCartService.getCartSummary();
//		
//		// Return the cart summary (updated item count and total price)
//		Map<String, Object> response = new HashMap<>();
//		response.put("itemCount", cartSummary.getItemCount());
//		response.put("totalPrice", cartSummary.getTotalPrice());
//		
//		System.out.println("SAVED");
//		return ResponseEntity.ok(response); // Return as JSON
//	}
	

}
