package sg.nus.iss.service.ecommerceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Webconfig {

	@Autowired
 private MyUserDetailService myUserDetailService;
	

	@Bean  //whenever we want spring to manage any bean, we should annotate it with bean
	//in this method we can  control/ customize the authorization
       SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
		//get httpSecurity registry and do the necessary customization
		//initially we are telling that "/" url should accessed by everyone 
		httpSecurity
		.csrf(httpSecurityCsrfConfigurer-> httpSecurityCsrfConfigurer.disable())
		.authorizeHttpRequests(registry -> registry
				.requestMatchers("/","/login", "/login-check","/submit-password", "/assets/**").permitAll()
				.requestMatchers("/createAccount","/register").hasRole("ADMIN")
//				.requestMatchers("/cart/**").hasRole("User")
				.anyRequest().authenticated()); //url not mentioned here requires authentication
		//so in order to read the login page, we need to provide login and we can do customization inside it
		//other than permitting, we can do customization here
		httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/", true)
				.permitAll());
		return httpSecurity.build();
		
	}
	
	
	
	//connect user table to spring security, for that we are using customer (MyUserDetailService) class which implements 
	//default UserDetailsService Interface
	@Bean
	 UserDetailsService userDetailService() {
		return myUserDetailService; 
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		System.out.println("provider");
		provider.setUserDetailsService(myUserDetailService);
		provider.setPasswordEncoder(passwordEncoder() );
		return provider;
	}
	
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}