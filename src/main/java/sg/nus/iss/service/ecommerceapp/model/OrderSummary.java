package sg.nus.iss.service.ecommerceapp.model;

import java.util.List;
import java.util.Map;

public class OrderSummary {

	private Order order;
	private double shippingFee;
	private double discountsApplied;
	private double totalProductPrice;
	private double finalTotal;
	private List<Map<String, Object>> groupedItems;
	
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

	public List<Map<String, Object>> getGroupedItems() {
		return groupedItems;
	}

	public void setGroupedItems(List<Map<String, Object>> groupedItems) {
		this.groupedItems = groupedItems;
	}

}
