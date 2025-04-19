package sg.nus.iss.service.ecommerceapp.model;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "users")
public class Customer extends User {
	
	@NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{8}$", message = "Please enter a valid number")
	 	@Column(name="mobile_phone_number")
	private String mobilePhoneNumber;
	
	public Customer(int id, String firstName, String lastName, String userName, String email,
			String mobilePhoneNumber, String password, String role) {
		super(id, firstName, lastName, userName, email, password,role);
		this.setMobilePhoneNumber(mobilePhoneNumber);
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}


	@OneToOne(mappedBy = "customer",  cascade = CascadeType.ALL)
	private ShoppingCart shoppingCart;
	
	@OneToMany(mappedBy="id",  cascade = CascadeType.ALL)
	private List<DeliveryAddress> deliveryAddresses;

	public List<DeliveryAddress> getDeliveryAddresses() {
		return deliveryAddresses;
	}

	public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
		this.deliveryAddresses = deliveryAddresses;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
  }
	

	}


