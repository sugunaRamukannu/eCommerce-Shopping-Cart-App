package sg.nus.iss.service.ecommerceapp.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Customer extends User {
	
//	@NotBlank(message = "Code is required")
//	@Size(min=8,max = 8, message = "Invalid mobile number")
//	@Column(name="mobile_phone_number")
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
