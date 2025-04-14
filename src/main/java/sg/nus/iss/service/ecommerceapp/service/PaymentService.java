package sg.nus.iss.service.ecommerceapp.service;

import sg.nus.iss.service.ecommerceapp.model.Order;

public interface PaymentService {

	String generateOrderId();
	Order createOrder(String mobilePhoneNumber);
	Order confirmOrder(String deliveryAddress, String paymentMethod, String mobilePhoneNumber);
}
