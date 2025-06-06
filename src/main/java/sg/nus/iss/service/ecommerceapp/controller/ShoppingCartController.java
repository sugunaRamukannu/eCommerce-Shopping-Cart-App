package sg.nus.iss.service.ecommerceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;
import sg.nus.iss.service.ecommerceapp.utils.PriceModifierUtil;

//Author(s): Ramukannu Suguna, Andy Teow Rui Qing

@Controller
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@GetMapping("/cart")
	public String showCart(Model model, Authentication authentication) {
		String mobilePhoneNumber = authentication.getName();
		List<CartItem> cartItems = shoppingCartService.listItemInCart(mobilePhoneNumber);
		model.addAttribute("cartItems", cartItems);
		return "cart";
	}

	// adddtocart
	@PostMapping("/cart/{productId}")
	public String addToCart(@PathVariable int productId, Model model, Authentication authentication,
			@RequestHeader(value = "Referer", required = false) String referer) {
		String mobilePhoneNumber = authentication.getName();
		shoppingCartService.addProductToCart(productId, mobilePhoneNumber);
		return "redirect:" + (referer != null ? referer : "/");
	}

	// remove cart items
	@PostMapping("/cart/delete")
	public String deleteProductFromCart(@RequestParam("productId") int productId, Model model,
			Authentication authentication) {
		String mobilePhoneNumber = authentication.getName();
		shoppingCartService.deleteProductFromCart(productId, mobilePhoneNumber);
		return "redirect:/cart";
	}

	// delete entire cart items
	@PostMapping("/cart/empty")
	public String emptyCartByMobileNumber(Authentication authentication) {
		String mobilePhoneNumber = authentication.getName();
		shoppingCartService.emptyCartByMobileNumber(mobilePhoneNumber);
		return "redirect:/cart";
	}

	// checkout page
	@PostMapping("/cart/checkout")
	public String checkoutSelectedItems(@RequestParam("checkedoutItems") List<Integer> itemIds, Model model,
			Authentication authentication) {
		String mobilePhoneNumber = authentication.getName();
		shoppingCartService.updateCheckedoutStatus(itemIds, mobilePhoneNumber);
		List<CartItem> checkedoutItems = shoppingCartService.showCheckedoutItems(mobilePhoneNumber);
		double totalPrice = checkedoutItems.stream()
				.mapToDouble(checkedoutItem -> (checkedoutItem.getPrice() * checkedoutItem.getQuantity())).sum();
		double finalTotal = totalPrice + PriceModifierUtil.SHIPPING_FEE - PriceModifierUtil.DISCOUNTS_APPLIED;
		model.addAttribute("shippingFee", PriceModifierUtil.SHIPPING_FEE);
		model.addAttribute("discountsApplied", PriceModifierUtil.DISCOUNTS_APPLIED);
		model.addAttribute("checkedoutItems", checkedoutItems);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("finalTotal", finalTotal);

		return "checkout";
	}

}
