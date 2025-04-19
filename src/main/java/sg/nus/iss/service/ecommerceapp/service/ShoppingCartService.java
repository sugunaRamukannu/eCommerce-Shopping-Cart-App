package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.ShoppingCart;

public interface ShoppingCartService {

	List<CartItem> listItemInCart(String mobilePhoneNumber);
	
	ShoppingCart addProductToCart(int productId, String mobilePhoneNumber);
	
	CartSummary getCartSummary(String mobilePhoneNumber);
	
	void checkoutCurrentUserSelectedItems(List<Integer> itemIds, String mobilePhoneNumber);
	
	List<CartItem> showCheckedoutItems(String mobilePhoneNumber);
	
	void updateCheckedoutStatus(List<Integer> selectedItemIds, String mobilePhoneNumber);
	
	void deleteProductFromCart(int productId, String mobilePhoneNumber);


	void emptyCartByMobileNumber(String mobilePhoneNumber);

}
