package sg.nus.iss.service.ecommerceapp.service;

import sg.nus.iss.service.ecommerceapp.model.Order;
import sg.nus.iss.service.ecommerceapp.model.OrderSummary;

public interface PaymentService {

	String generateOrderId();
	OrderSummary getOrderSummary(String orderId);
	Order confirmOrder(String deliveryAddress, String paymentMethod, String mobilePhoneNumber);
}
