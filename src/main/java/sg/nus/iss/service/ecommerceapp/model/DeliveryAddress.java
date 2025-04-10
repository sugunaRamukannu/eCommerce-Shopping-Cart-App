package sg.nus.iss.service.ecommerceapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="delivery_addresses")
public class DeliveryAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="delivery_address_id")
	private int id;
	private String address;

	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	public DeliveryAddress() {}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return address;
	}
}
