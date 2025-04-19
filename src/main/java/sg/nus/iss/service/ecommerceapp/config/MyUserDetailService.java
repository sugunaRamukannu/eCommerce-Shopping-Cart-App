package sg.nus.iss.service.ecommerceapp.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.Customer;
import sg.nus.iss.service.ecommerceapp.service.CustomerService;

@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private CustomerService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Customer> user = userService.findBymobilePhoneNumber(username);

		if (user.isEmpty()) {
		
			throw new UsernameNotFoundException("Invalid user");
		}

		// GrantedAuthority is an interface used by Spring Security to manage roles and
		// permissions.

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRole()));
	
		// returning the user details
		return new org.springframework.security.core.userdetails.User(user.get().getMobilePhoneNumber(),
				user.get().getPassword(), authorities);
	}

}
