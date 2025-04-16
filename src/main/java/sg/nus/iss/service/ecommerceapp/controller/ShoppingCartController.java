package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;

@Controller
public class ShoppingCartController {

	// add products to cart
	// list of products, click on add button, product gets added into shopping cart
	// pull product details from product repository
	// display products in shopping cart
	// store products in shopping cart repository

	@Autowired
	private ShoppingCartService shoppingCartService;

	@GetMapping("/cart")
	public String showCart(Model model, Authentication authentication) {
//		List<CartItem> cartItems = shoppingCartService.listItemInCart();
//		model.addAttribute("cartItems", cartItems);

		String mobilePhoneNumber = authentication.getName();

		Map<Product, List<CartItem>> groupedItems = shoppingCartService.listItemInCart(mobilePhoneNumber);

		model.addAttribute("groupedItems", groupedItems);

		CartSummary cartSummary = shoppingCartService.getCartSummary(mobilePhoneNumber);
		model.addAttribute("totalPrice", cartSummary.getTotalPrice());
		model.addAttribute("itemCount", cartSummary.getItemCount());

//		List<DeliveryAddress> addresses = shoppingCartService.findDeliveryAddressesByCustomer(mobilePhoneNumber);
//		model.addAttribute("addresses", addresses);

		return "cart";
	}

	@PostMapping("/cart/{productId}")
	public String addToCart(@PathVariable int productId, Model model, Authentication authentication) {
		
		String mobilePhoneNumber = authentication.getName();

		shoppingCartService.addProductToCart(productId, mobilePhoneNumber);
		System.out.println("PRODUCT ADDED");

		return "redirect:/";
	}

//	@PostMapping("/cart")
//	public CartSummary addToCart(int productId) {
//		shoppingCartService.addProductToCart(productId);
//		System.out.println("Hello");
//		
//		return shoppingCartService.getCartSummary();
//	}

	@PostMapping("/cart/delete")
	public String deleteProductFromCart(@RequestParam("productId") int productId, Model model, Authentication authentication) {

		String mobilePhoneNumber = authentication.getName();

		shoppingCartService.deleteProductFromCart(productId, mobilePhoneNumber);

		return "redirect:/cart";
	}

	@PostMapping("/cart/empty")
	public String emptyCart() {

		shoppingCartService.emptyCart();
		return "redirect:/cart";
	}

	// proceed to checkout button clicked
	// goes into the checkout page
	// displays selected products
	// customer to fill in shipping address
	// apply discounts if any
	// proceed to payment button

	// retain checked UI
	// increase checked box size
	// if same product, the quantity should increase and not have a separate row

//	@GetMapping("/cart/checkout")
//	public String showCheckoutItems(Model model) {
//		List<CartItem> checkedoutItems = shoppingCartService.showCheckedoutItems();
//		
//		double total = checkedoutItems.stream()
//		        .mapToDouble(item -> item.getPrice() * item.getQuantity())
//		        .sum();
//		
//		model.addAttribute("checkedoutItems", checkedoutItems);
//		model.addAttribute("totalPrice", total);
//		
//		return "checkout";
//	}
	@GetMapping("/cart/checkout")
	public String showCheckedoutItems(Model model, Authentication authentication) {

		String mobilePhoneNumber = authentication.getName();

		Map<Product, List<CartItem>> checkedoutItems = shoppingCartService.showCheckedoutItems(mobilePhoneNumber);

		double totalPrice = checkedoutItems.entrySet().stream().mapToDouble(
				entry -> entry.getKey().getPrice() * entry.getValue().stream().mapToInt(CartItem::getQuantity).sum())
				.sum();

		double shippingFee = 5.00;
		double discountsApplied = 0.00;
		
		double finalTotal = totalPrice + shippingFee - discountsApplied;
		
		model.addAttribute("shippingFee", shippingFee);
		model.addAttribute("discountsApplied", discountsApplied);
		model.addAttribute("checkedoutItems", checkedoutItems);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("finalTotal", finalTotal);
		
		return "checkout";
	}

	@PostMapping("/cart/checkout")
	public String checkoutSelectedItems(@RequestParam("checkedoutItems") List<Integer> itemIds, Model model,
			Authentication authentication) {

//		 if (itemIds == null || itemIds.isEmpty()) {
//		        // Handle the case where no items are selected
//		        model.addAttribute("errorMessage", "No items selected for checkout.");
//		        
//		        List<CartItem> cartItems = shoppingCartService.listItemInCart();
//				model.addAttribute("cartItems", cartItems);
//				
//				CartSummary cartSummary = shoppingCartService.getCartSummary();
//				model.addAttribute("totalPrice", cartSummary.getTotalPrice());
//				model.addAttribute("itemCount", cartSummary.getItemCount());
//				
//		        return "cart"; // Redirect back to cart if no items are selected
//		    }

		String mobilePhoneNumber = authentication.getName();
		
		shoppingCartService.updateCheckedoutStatus(itemIds, mobilePhoneNumber);

		Map<Product, List<CartItem>> checkedoutItems = shoppingCartService.showCheckedoutItems(mobilePhoneNumber);

		double totalPrice = checkedoutItems.entrySet().stream().mapToDouble(
				entry -> entry.getKey().getPrice() * entry.getValue().stream().mapToInt(CartItem::getQuantity).sum())
				.sum();

		double shippingFee = 5.00;
		double discountsApplied = 0.00;
		
		double finalTotal = totalPrice + shippingFee - discountsApplied;
		
		model.addAttribute("shippingFee", shippingFee);
		model.addAttribute("discountsApplied", discountsApplied);
		model.addAttribute("checkedoutItems", checkedoutItems);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("finalTotal", finalTotal);

		return "checkout";
	}

//	@GetMapping("/cart/back")
//	public String backToCart(HttpSession session) {
//		shoppingCartService.uncheckedCurrentUserItems();

//		List<CartItem> cartItems = shoppingCartService.listItemInCart();
//		model.addAttribute("cartItems", cartItems);
//		
//		CartSummary cartSummary = shoppingCartService.getCartSummary();
//		model.addAttribute("totalPrice", cartSummary.getTotalPrice());
//		model.addAttribute("itemCount", cartSummary.getItemCount());

//		return "redirect:/cart";
//	}

}
