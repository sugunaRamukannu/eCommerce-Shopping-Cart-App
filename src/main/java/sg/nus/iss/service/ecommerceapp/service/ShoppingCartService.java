package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;
import sg.nus.iss.service.ecommerceapp.model.ShoppingCart;

public interface ShoppingCartService {

	List<CartItem> listItemInCart();
	ShoppingCart addProductToCart(int productId);
	CartSummary getCartSummary();
	List<DeliveryAddress> findDeliveryAddressesByCustomer(int customerId);
	void emptyCart();

}
