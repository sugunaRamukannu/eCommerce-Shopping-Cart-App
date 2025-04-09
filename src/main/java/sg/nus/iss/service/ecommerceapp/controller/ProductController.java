package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;
import sg.nus.iss.service.ecommerceapp.service.ProductService;
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/")
	public String displayProducts(Model model) {
		List<Product> products = productService.listAllProducts();
		model.addAttribute("products", products);
		
		return "index";
	}
	
	@GetMapping("/products")
	public String displayProductsPage() {
		return "products";
	}
	
	@PostMapping("/add-to-cart/{productId}")
	public String addToCart(@PathVariable int productId) {
		
		shoppingCartService.addProductToCart(productId);
		System.out.println("PRODUCT ADDED");
		
		return "redirect:/";
	}
	
	@PostMapping("/cart/empty")
	public String emptyCart() {
		
		shoppingCartService.emptyCart();
		return "redirect:/cart";
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
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
