package sg.nus.iss.service.ecommerceapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import sg.nus.iss.service.ecommerceapp.exception.CustomerNotFoundException;
import sg.nus.iss.service.ecommerceapp.exception.ProductNotFoundException;
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
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;

//Author(s): Ramukannu Suguna, Andy Teow Rui Qing

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
		// Retrieve the customer by mobile phone number
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = customer.getShoppingCart();

			// Create a new shopping cart if none exists
			if (shoppingCart == null) {
				shoppingCart = new ShoppingCart();
				customer.setShoppingCart(shoppingCart);
				shoppingCartRepository.save(shoppingCart);
			}

			// Retrieve the product by ID
			Optional<Product> optionalProduct = productRepository.findById(productId);

			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();

				CartItem exsistingItem = null;
				if(shoppingCart.getItems() != null) {
					for (CartItem c : shoppingCart.getItems()) {
						if (c.getProduct().getId() == product.getId()) {
							exsistingItem = c;
						}
					}
				}
			

				if (exsistingItem != null) {
					exsistingItem.setQuantity(exsistingItem.getQuantity() + 1);
					cartItemRepository.save(exsistingItem);
				} else {
					// Create a new cart item and add it to the cart
					CartItem cartItem = new CartItem();
					cartItem.setProduct(product);
					cartItem.setShoppingCart(shoppingCart);
					cartItem.setQuantity(1);
					cartItem.setPrice(product.getPrice());
					cartItemRepository.save(cartItem);
					// Initialize the items list if it's null
					if (shoppingCart.getItems() == null) {
						shoppingCart.setItems(new ArrayList<>());
					}
					shoppingCart.getItems().add(cartItem);
				}
				// Set the customer for the shopping cart
				shoppingCart.setCustomer(customer);
				customerRepository.save(customer);
				return shoppingCart;
			} else {
				throw new ProductNotFoundException("Product not found with ID: " + productId);
			}
		} else {
			throw new CustomerNotFoundException("Customer not found with mobile number: " + mobilePhoneNumber);
		}
	}

	@Override
	public List<CartItem> listItemInCart(String mobilePhoneNumber) {

		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		if (optionalCustomer.isPresent()) {
			ShoppingCart shoppingCart = optionalCustomer.get().getShoppingCart();
			if (shoppingCart != null) {
				List<CartItem> cartItems = shoppingCart.getItems();
				return cartItems;
			}
		}

		return new ArrayList<>();
	}

	@Override
	public CartSummary getCartSummary(String mobilePhoneNumber) {

		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

		if (optionalCustomer.isPresent()) {
			ShoppingCart shoppingCart = optionalCustomer.get().getShoppingCart();

			if (shoppingCart != null) {
				int itemCount = shoppingCart.getItems().stream().mapToInt(item -> item.getQuantity()).sum();
				double totalPrice = shoppingCart.getItems().stream().mapToDouble(item -> item.getProduct().getPrice())
						.sum();

				return new CartSummary(itemCount, totalPrice);
			}
		}
		return new CartSummary(0, 0.0);
	}

	@Override
	public void checkoutCurrentUserSelectedItems(List<Integer> itemIds, String mobilePhoneNumber) {
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = customer.getShoppingCart();

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
	public List<CartItem> showCheckedoutItems(String mobilePhoneNumber) {
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			return customer.getShoppingCart().getItems().stream().filter(cart -> cart.getCheckedOut()).toList();
		}

		return new ArrayList<>();
	}

	@Override
	public void updateCheckedoutStatus(List<Integer> selectedItemIds, String mobilePhoneNumber) {
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = customer.getShoppingCart();
			if (shoppingCart != null) {
				List<CartItem> allCartItems = shoppingCart.getItems();
				for (CartItem item : allCartItems) {
					boolean isSelected = selectedItemIds.contains(item.getId());
					item.setCheckedOut(isSelected);
				}
				cartItemRepository.saveAll(allCartItems);
			}
		}
	}

	@Override
	@Transactional
	public void deleteProductFromCart(int productId, String mobilePhoneNumber) {

		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = customer.getShoppingCart();

			if (shoppingCart != null) {

				if (shoppingCart.getItems().size() == 1) {
					customer.setShoppingCart(null);
					customerRepository.save(customer);
					shoppingCartRepository.deleteByCustomerId(customer.getId());
				}

				cartItemRepository.deleteById(productId);
			}

		}
	}

	@Override
	@Transactional
	public void emptyCartByMobileNumber(String mobilePhoneNumber) {
		Optional<Customer> customerOpt = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setShoppingCart(null);
			customerRepository.save(customer);
			ShoppingCart cart = shoppingCartRepository.findByCustomerId(customer.getId());
			if (cart != null) {
				shoppingCartRepository.deleteByCustomerId(customer.getId());
				cartItemRepository.deleteAll(cart.getItems());
			}
		} else {
			throw new EntityNotFoundException("Customer with mobile number " + mobilePhoneNumber + " not found.");
		}
	}

}
