package sg.nus.iss.service.ecommerceapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderSummary;
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

	// generateOrderId
	// listOrderItems
	// processPayment
	// clearCheckedoutItemsfromCart
	// updateOrderStatus
	
	@Override
	public String generateOrderId() {

		String orderIdString = "ORD-" + System.currentTimeMillis() + "-"
				+ UUID.randomUUID().toString().substring(0, 8).toUpperCase();

		return orderIdString;
	}

	@Override
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

				for (List<CartItem> itemGroup : groupedItems.values()) {
					for (CartItem items : itemGroup) {
						items.setOrder(order);
					}
				}

				// flatten items as setItems expects List<CartItem>, not
				// Collection<List<CartItem>>
				List<CartItem> allItems = groupedItems.values().stream().flatMap(List::stream)
						.collect(Collectors.toList());

				order.setOrderItems(allItems);
				orderRepository.save(order);

				return order;
			}
		}

		throw new RuntimeException("Unable to place order: customer or cart not found.");
	}

	@Override
	public OrderSummary getOrderSummary(String orderId) {

		Order order = orderRepository.findByOrderId(orderId);

		if (order == null)
			throw new RuntimeException("No order placed");

		double shippingFee = 5.00;
		double discountsApplied = 0.0;
		double totalProductPrice = order.getOrderItems().stream()
				.map(item -> item.getProduct().getPrice() * item.getQuantity()).reduce(0.0, Double::sum);
		double finalTotal = totalProductPrice + shippingFee - discountsApplied;

		Map<Product, List<CartItem>> grouped = order.getOrderItems().stream()
				.collect(Collectors.groupingBy(CartItem::getProduct, LinkedHashMap::new, Collectors.toList()));

		List<Map<String, Object>> groupedItems = new ArrayList<>();

		for (Map.Entry<Product, List<CartItem>> entry : grouped.entrySet()) {
			Product product = entry.getKey();
			int totalQty = entry.getValue().stream().mapToInt(CartItem::getQuantity).sum();
			double subtotal = product.getPrice() * totalQty;

			Map<String, Object> map = new HashMap<>();
			map.put("product", product);
			map.put("quantity", totalQty);
			map.put("subtotal", subtotal);
			groupedItems.add(map);
		}

		OrderSummary summary = new OrderSummary();
		summary.setOrder(order);
		summary.setTotalProductPrice(totalProductPrice);
		summary.setShippingFee(shippingFee);
		summary.setDiscountsApplied(discountsApplied);
		summary.setFinalTotal(finalTotal);
		summary.setGroupedItems(groupedItems);

		return summary;
	}

//	public Order createOrder(String mobilePhoneNumber) {
//		
//		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);
//		
//		Order order = new Order();
////		if (optionalCustomer.isEmpty()) {
////      throw new IllegalArgumentException("Customer not found");
////  }
//		if (optionalCustomer.isPresent()) {
//			Customer customer = optionalCustomer.get();
//			ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
//			
//			if (shoppingCart != null) {
//				List<CartItem> checkedoutItems = cartItemRepository.findByShoppingCartAndCheckedOut(shoppingCart, true);
//				
//				Map<Product, List<CartItem>> groupedItems = checkedoutItems.stream()
//						.collect(Collectors.groupingBy(CartItem::getProduct, LinkedHashMap::new, Collectors.toList()));
//				 
//			    order.setCustomer(customer);
//			    order.setDate(LocalDateTime.now());
//			    order.setStatus("PENDING");
//			    order.setOrderIdString(generateOrderId());
//			    
//			    for(List<CartItem> itemGroup : groupedItems.values()) {
//			    	for (CartItem items : itemGroup) {
//			    		items.setOrder(order);
//			    	}
//			    }
//			    
//			    //flatten items as setItems expects List<CartItem>, not Collection<List<CartItem>>
//			    List<CartItem> allItems = groupedItems.values().stream()
//		                .flatMap(List::stream)
//		                .collect(Collectors.toList());
//
//		        order.setOrderItems(allItems);
//			    
//			    orderRepository.save(order);
//				
//				return order;
//			}
//		}
//		
//		return null;
//		}		
}
