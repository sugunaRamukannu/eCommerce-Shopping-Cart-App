package sg.nus.iss.service.ecommerceapp.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ShoppingCart;
import sg.nus.iss.service.ecommerceapp.repository.CartItemRepository;
import sg.nus.iss.service.ecommerceapp.repository.CustomerRepository;
import sg.nus.iss.service.ecommerceapp.repository.OrderRepository;
import sg.nus.iss.service.ecommerceapp.repository.ShoppingCartRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	//generateOrderId
	//listOrderItems
	//processPayment
	//clearCheckedoutItemsfromCart
	//updateOrderStatus
	public String generateOrderId() {

		String orderIdString = "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		
		return orderIdString;
	}
	
	public Order confirmOrder(String deliveryAddress, String paymentMethod, String mobilePhoneNumber) {
		
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		
		Order order = new Order();
		
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
			
			if (shoppingCart != null) {
				List<CartItem> checkedoutItems = cartItemRepository.findByShoppingCartAndCheckedOut(shoppingCart, true);

				Map<Product, List<CartItem>> groupedItems = checkedoutItems.stream()
						.collect(Collectors.groupingBy(CartItem::getProduct, LinkedHashMap::new, Collectors.toList()));
				
				order.setCustomer(customer);
				order.setDate(LocalDateTime.now());
				order.setDeliveryAddress(deliveryAddress);
				order.setStatus("PAID");
				order.setPaymentMethod(paymentMethod);
				order.setOrderIdString(generateOrderId());
				order.setOrderItems(checkedoutItems);
		
				for(List<CartItem> itemGroup : groupedItems.values()) {
			    	for (CartItem items : itemGroup) {
			    		items.setOrder(order);
			    	}
				}
			    	
			    	 //flatten items as setItems expects List<CartItem>, not Collection<List<CartItem>>
				    List<CartItem> allItems = groupedItems.values().stream()
			                .flatMap(List::stream)
			                .collect(Collectors.toList());

			        order.setOrderItems(allItems);
			        orderRepository.save(order);
			        
			        return order;
			}
		}
		
		return null;
	}
	
	public Order createOrder(String mobilePhoneNumber) {
		
		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
		
		Order order = new Order();
//		if (optionalCustomer.isEmpty()) {
//      throw new IllegalArgumentException("Customer not found");
//  }
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
			
			if (shoppingCart != null) {
				List<CartItem> checkedoutItems = cartItemRepository.findByShoppingCartAndCheckedOut(shoppingCart, true);
				
				Map<Product, List<CartItem>> groupedItems = checkedoutItems.stream()
						.collect(Collectors.groupingBy(CartItem::getProduct, LinkedHashMap::new, Collectors.toList()));
				 
			    order.setCustomer(customer);
			    order.setDate(LocalDateTime.now());
			    order.setStatus("PENDING");
			    order.setOrderIdString(generateOrderId());
			    
			    for(List<CartItem> itemGroup : groupedItems.values()) {
			    	for (CartItem items : itemGroup) {
			    		items.setOrder(order);
			    	}
			    }
			    
			    //flatten items as setItems expects List<CartItem>, not Collection<List<CartItem>>
			    List<CartItem> allItems = groupedItems.values().stream()
		                .flatMap(List::stream)
		                .collect(Collectors.toList());

		        order.setOrderItems(allItems);
			    
			    orderRepository.save(order);
				
				return order;
			}
		}
		
		return null;
		}		
}
