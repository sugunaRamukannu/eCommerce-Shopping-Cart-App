package sg.nus.iss.service.ecommerceapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ShoppingCart;
import sg.nus.iss.service.ecommerceapp.repository.CartItemRepository;
import sg.nus.iss.service.ecommerceapp.repository.CustomerRepository;
import sg.nus.iss.service.ecommerceapp.repository.ProductRepository;
import sg.nus.iss.service.ecommerceapp.repository.ShoppingCartRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	public ShoppingCart addProductToCart(int productId) {
		//For Spring security 
		String phoneNumber = "999";
		
		Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(phoneNumber);
		
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = customer.getShoppingCart();
			
			if (shoppingCart == null) {
				
				shoppingCart = new ShoppingCart();
				customer.setShoppingCart(shoppingCart);
				shoppingCartRepository.save(shoppingCart);
			}

		Optional<Product> optionalProduct = productRepository.findById(productId);
		
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setShoppingCart(shoppingCart);
			cartItem.setQuantity(1);
			cartItem.setPrice(product.getPrice());
			cartItemRepository.save(cartItem);
			shoppingCart.getItems().add(cartItem);
		}
		
		System.out.println("ADDED");
		customerRepository.save(customer);
		
		return shoppingCart;
		} else {
			return new ShoppingCart();
		}
		
	}
	
	public List<CartItem> listItemInCart() {
		
		String phoneNumber = "999";
		
		Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(phoneNumber);
		
		if (optionalCustomer.isPresent()) {
			ShoppingCart shoppingCart = optionalCustomer.get().getShoppingCart();
			
			if (shoppingCart != null) {
				return shoppingCart.getItems();
			}
		}
		System.out.println("LIST");
		return new ArrayList<>();
	}
	
	public CartSummary getCartSummary() {
		
		String phoneNumber = "999";
		
		Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(phoneNumber);
		
		if (optionalCustomer.isPresent()) {
			ShoppingCart shoppingCart = optionalCustomer.get().getShoppingCart();
			
			if (shoppingCart != null) {
				int itemCount = shoppingCart.getItems().size();
				double totalPrice = shoppingCart.getItems().stream()
						.mapToDouble(item -> item.getProduct().getPrice())
						.sum();
				
				return new CartSummary(itemCount, totalPrice);
			}
		}
		System.out.println("SUMMARY");
		return new CartSummary(0, 0.0);
	}
}
		