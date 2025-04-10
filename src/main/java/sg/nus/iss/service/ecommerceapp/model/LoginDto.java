package sg.nus.iss.service.ecommerceapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class LoginDto {
	@NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{8}$", message = "Please enter a valid number")
		private String username;
		private String password;
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}

}
