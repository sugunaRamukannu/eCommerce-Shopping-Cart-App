package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;
import java.util.Map;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ShoppingCart;

public interface ShoppingCartService {

//	List<CartItem> listItemInCart();
	Map<Product, List<CartItem>> listItemInCart();
	ShoppingCart addProductToCart(int productId);
	CartSummary getCartSummary();
	void checkoutCurrentUserSelectedItems(List<Integer> itemIds);
	Map<Product, List<CartItem>> showCheckedoutItems();
	void updateCheckedoutStatus(List<Integer> selectedItemIds);
	List<DeliveryAddress> findDeliveryAddressesByCustomer(int customerId);
//	void uncheckedCurrentUserItems();
	void deleteProductFromCart(int productId);
	void emptyCart();

}
