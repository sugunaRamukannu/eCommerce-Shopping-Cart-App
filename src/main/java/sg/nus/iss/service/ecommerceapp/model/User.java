package sg.nus.iss.service.ecommerceapp.model;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@MappedSuperclass
public abstract class User implements UserDetails{
	
	
	 @Id
	 @GeneratedValue(strategy = 
	GenerationType.IDENTITY)
	private int id;
	 @NotBlank(message = "Name is required")
	 @Column(name="first_name")
	private String firstName;
	 @NotBlank(message = "Name is required")
	 @Column(name="last_name")
	private String lastName;
	 @Column(name="user_name")
	private String userName;
	 
	private String email;
	private String role;
	@Column(name = "password")
	private String password;
	
	public User() {};
	public User(int id, String firstName, String lastName, String userName, String email, String password, String role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role=role;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
