package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.repository.ProductCategoryRepository;
import sg.nus.iss.service.ecommerceapp.service.ProductCategoryService;
import sg.nus.iss.service.ecommerceapp.service.ProductService;
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	//not supposed to use repository in the controller, as we are already using service layer
//	@Autowired
//	private ProductCategoryRepository productCategoryRepository;
	
	@GetMapping("/")
    public String homePage(Model model, Authentication authentication) {

//     System.out.println(authentication.getName());
//     System.out.println(authentication.getAuthorities());
//     
    	
		List<Product> products = productService.findAllProducts();
    	List<ProductCategory> categories = productCategoryService.findAllCategories();   	
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
        System.out.println("productspage");
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
	public String searchProducts(
	    @RequestParam("keyword") String keyword,
	    @RequestParam("searchtype") String searchType,
	    Model model) {

	}
	

	@GetMapping("/cart")
	public String displayCart() {
		return "cart";

	}
	
	
	@PostMapping("/cart/{productId}")
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
	

}
