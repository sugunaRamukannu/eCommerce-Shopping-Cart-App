package sg.nus.iss.service.ecommerceapp.model;

import java.util.List;

public class OrderSummary {

	private Order order;
	private double shippingFee;
	private double discountsApplied;
	private double totalProductPrice;
	private double finalTotal;
	private List<OrderItem> orderItems;
	
	public OrderSummary() {}
	
	public OrderSummary(Order order, double totalProductPrice, double shippingFee, double discountsApplied, double finalTotal) {
		this.setOrder(order);
		this.setTotalProductPrice(totalProductPrice);
		this.setShippingFee(shippingFee);
		this.setDiscountsApplied(discountsApplied);
		this.setFinalTotal(finalTotal);
	}

	public double getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(double finalTotal) {
		this.finalTotal = finalTotal;
	}

	public double getTotalProductPrice() {
		return totalProductPrice;
	}

	public void setTotalProductPrice(double totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}

	public double getDiscountsApplied() {
		return discountsApplied;
	}

	public void setDiscountsApplied(double discountsApplied) {
		this.discountsApplied = discountsApplied;
	}

	public double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}
