package sg.nus.iss.service.ecommerceapp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.CartItem;
import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.model.DeliveryAddress;
import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderItem;
import sg.nus.iss.service.ecommerceapp.model.OrderSummary;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.repository.CartItemRepository;
import sg.nus.iss.service.ecommerceapp.repository.CustomerRepository;
import sg.nus.iss.service.ecommerceapp.repository.DeliveryAddressRepository;
import sg.nus.iss.service.ecommerceapp.repository.OrderRepository;
import sg.nus.iss.service.ecommerceapp.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DeliveryAddressRepository deliveryAddressRepository;

	@Override
	public Order confirmOrder(String deliveryAddress, String paymentMethod, String mobilePhoneNumber) {

		Optional<Customer> optionalCustomer = customerRepository.findByMobilePhoneNumber(mobilePhoneNumber);

		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			if (customer.getShoppingCart() != null) {
				List<CartItem> checkedoutItems = customer.getShoppingCart().getItems().stream()
						.filter(cart -> cart.getCheckedOut()).toList();
				Order order = new Order();
				order.setCustomer(customer);
				order.setDate(LocalDateTime.now());
				order.setDeliveryAddress(deliveryAddress);
				order.setStatus("PAID");
				order.setPaymentMethod(paymentMethod);
				order.setOrderIdString(generateOrderId());

				List<OrderItem> orderItems = new ArrayList<>();

				double accumulatedPrice = 0.0;

				double shippingFee = 5.00;
				double discountsApplied = 0.0;

				for (CartItem checkedoutItem : checkedoutItems) {
					Product product = checkedoutItem.getProduct();
					int totalQuantity = checkedoutItem.getQuantity();
					OrderItem orderItem = new OrderItem();
					orderItem.setProduct(product);
					orderItem.setQuantity(totalQuantity);
					orderItem.setPurchasePrice(product.getPrice() * totalQuantity);
					accumulatedPrice += orderItem.getPurchasePrice();
					orderItem.setOrder(order);
					orderItems.add(orderItem);
				}
				order.setTotalPrice(accumulatedPrice + shippingFee - discountsApplied);
				order.setOrderItems(orderItems);

				orderRepository.save(order);
				
				DeliveryAddress address = new DeliveryAddress();
				address.setCustomer(customer);
				address.setAddress(deliveryAddress);

				deliveryAddressRepository.save(address);

				cartItemRepository.deleteAll(checkedoutItems);

				return order;
			}
		}

		throw new RuntimeException("Unable to place order: customer or cart not found.");
	}

	@Override
	public OrderSummary getOrderSummary(String orderId) {

		Order order = orderRepository.findByorderIdString(orderId);

		if (order == null)
			throw new RuntimeException("No order placed");

		double shippingFee = 5.00;
		double discountsApplied = 0.0;
		double totalProductPrice = order.getOrderItems().stream().mapToDouble(item -> item.getPurchasePrice()).sum();
		double finalTotal = totalProductPrice + shippingFee - discountsApplied;

		OrderSummary summary = new OrderSummary();
		summary.setOrder(order);
		summary.setTotalProductPrice(totalProductPrice);
		summary.setShippingFee(shippingFee);
		summary.setDiscountsApplied(discountsApplied);
		summary.setFinalTotal(finalTotal);
		summary.setOrderItems(order.getOrderItems());

		return summary;
	}

	private static String generateOrderId() {
		return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}
}
