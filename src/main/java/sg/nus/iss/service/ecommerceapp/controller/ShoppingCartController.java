package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;
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
	
	@GetMapping("/cart/{id}") //test with 1 first
	public String showCart(@PathVariable int id, Model model) {
		List<CartItem> cartItems = shoppingCartService.listItemInCart();
		model.addAttribute("cartItems", cartItems);
		
		CartSummary cartSummary = shoppingCartService.getCartSummary();
		model.addAttribute("totalPrice", cartSummary.getTotalPrice());
		model.addAttribute("itemCount", cartSummary.getItemCount());
		
		List<DeliveryAddress> addresses = shoppingCartService.findDeliveryAddressesByCustomer(id);
		model.addAttribute("addresses", addresses);
		
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
