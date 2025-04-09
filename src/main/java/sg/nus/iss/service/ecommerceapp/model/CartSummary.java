package sg.nus.iss.service.ecommerceapp.model;

public class CartSummary {

	private int itemCount;
	private double totalPrice;
	
	public CartSummary(int itemCount, double totalPrice) {
		this.itemCount = itemCount;
		this.totalPrice = totalPrice;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
}
