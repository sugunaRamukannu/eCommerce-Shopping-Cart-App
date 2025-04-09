package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;

@Controller
public class ShoppingCartController {

	//add products to cart
	//list of products, click on add button, product gets added into shopping cart
	//pull product details from product repository
	//display products in shopping cart
	//store products in shopping cart repository
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/cart")
	public String showCart(Model model) {
		List<CartItem> cartItems = shoppingCartService.listItemInCart();
		model.addAttribute("cartItems", cartItems);
		
		CartSummary cartSummary = shoppingCartService.getCartSummary();
		model.addAttribute("totalPrice", cartSummary.getTotalPrice());
		model.addAttribute("itemCount", cartSummary.getItemCount());
		
		return "cart";
	}
	
//	@PostMapping("/cart")
//	public CartSummary addToCart(int productId) {
//		shoppingCartService.addProductToCart(productId);
//		System.out.println("Hello");
//		
//		return shoppingCartService.getCartSummary();
//	}
	
}
