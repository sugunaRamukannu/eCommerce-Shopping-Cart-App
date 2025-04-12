package sg.nus.iss.service.ecommerceapp.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;
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
	
	

	@Override
	public ShoppingCart addProductToCart(int productId, String mobilePhoneNumber) {

		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

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

	@Override
	public Map<Product, List<CartItem>> listItemInCart(String mobilePhoneNumber) {

		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

		if (optionalCustomer.isPresent()) {
			ShoppingCart shoppingCart = optionalCustomer.get().getShoppingCart();

			if (shoppingCart != null) {
				List<CartItem> cartItems = shoppingCart.getItems();

				Map<Product, List<CartItem>> groupedItems = cartItems.stream()
						.collect(Collectors.groupingBy(CartItem::getProduct, LinkedHashMap::new, Collectors.toList()));

				return groupedItems;
			}
		}
		return new HashMap<>();
	}

//	public List<CartItem> listItemInCart() {
//		
//		String phoneNumber = "999";
//		
//		Optional<Customer> optionalCustomer = customerRepository.findBymobilePhoneNumber(phoneNumber);
//		
//		if (optionalCustomer.isPresent()) {
//			ShoppingCart shoppingCart = optionalCustomer.get().getShoppingCart();
//			
//			if (shoppingCart != null) {
//				return shoppingCart.getItems();
//			}
//		}
//		System.out.println("LIST");
//		return new ArrayList<>();
//	}

	@Override
	public CartSummary getCartSummary(String mobilePhoneNumber) {

		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

		if (optionalCustomer.isPresent()) {
			ShoppingCart shoppingCart = optionalCustomer.get().getShoppingCart();

			if (shoppingCart != null) {
				int itemCount = shoppingCart.getItems().size();
				double totalPrice = shoppingCart.getItems().stream().mapToDouble(item -> item.getProduct().getPrice())
						.sum();

				return new CartSummary(itemCount, totalPrice);
			}
		}
		System.out.println("SUMMARY");
		return new CartSummary(0, 0.0);
	}
	
	@Override
	public void checkoutCurrentUserSelectedItems(List<Integer> itemIds, String mobilePhoneNumber) {	
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
			
			if (shoppingCart != null) {	
				List<CartItem> items = cartItemRepository.findAllById(itemIds);
				for (CartItem item : items) {
					item.setCheckedOut(true);
				}
				cartItemRepository.saveAll(items);
			}
		}
	}
	
	@Override
	public Map<Product, List<CartItem>> showCheckedoutItems(String mobilePhoneNumber) {
		
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
			
			if (shoppingCart != null) {
				List<CartItem> checkedoutItems = cartItemRepository.findByCheckedOut(true);
				
				Map<Product, List<CartItem>> groupedItems = checkedoutItems.stream()
						.collect(Collectors.groupingBy(CartItem::getProduct, LinkedHashMap::new, Collectors.toList()));
				
				return groupedItems;
			}
		}
		
		return new HashMap<>();
	}
	
	@Override
	public void updateCheckedoutStatus(List<Integer> selectedItemIds, String mobilePhoneNumber) {
		
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
			
			if (shoppingCart != null) {
				List<CartItem> allCartItems = shoppingCart.getItems();
		
				for (CartItem item : allCartItems) {
					boolean isSelected = selectedItemIds.contains(item.getProduct().getId());
						item.setCheckedOut(isSelected);
				
					}
				
				cartItemRepository.saveAll(allCartItems);
			}
		}
	}
	
	//this is to unchecked ALL user items
//	public void uncheckedCurrentUserItems() {
//		
//		String phoneNumber = "999";
//		
//		Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(phoneNumber);
//		
//		if (optionalCustomer.isPresent()) {
//			Customer customer = optionalCustomer.get();
//			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
//			
//			if (shoppingCart != null) {
//				List<CartItem> checkedItems = cartItemRepository.findByCheckedOut(true);
//				for (CartItem checkedItem : checkedItems) {
//					checkedItem.setCheckedOut(false);
//				}
//		cartItemRepository.saveAll(checkedItems);
//			}
//		}
//	}
	
	@Override
	public void deleteProductFromCart(int productId, String mobilePhoneNumber) {
		
			Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		
			if (optionalCustomer.isPresent()) {
				Customer customer = optionalCustomer.get();
				ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
			
				if (shoppingCart != null) {
					List<CartItem> cartItems = shoppingCart.getItems();
					
					List<CartItem> toDelete = cartItems.stream()
							.filter(item -> item.getProduct().getId() == productId)
							.collect(Collectors.toList());
					
					cartItems.removeAll(toDelete);
					cartItemRepository.deleteAll(toDelete);
					shoppingCartRepository.save(shoppingCart);
				}
			}
	}

	@Override
	public void emptyCart() {
		cartItemRepository.deleteAll();
	}

	@Override
	public List<DeliveryAddress> findDeliveryAddressesByCustomer(String mobilePhoneNumber) {
		return customerRepository.findDeliveryAddressesByCustomer(mobilePhoneNumber);
	}
}
