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
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class Webconfig {

	@Autowired
	private MyUserDetailService myUserDetailService;

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
		// Disable CSRF if you're not dealing with a stateful application (like RESTful
		// APIs).
		httpSecurity

				// Authorization configuration - define which URLs are accessible by which
				// roles.
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/login-check", "/forgot-password", "/register", "/send-otp",
								"/reset-password", "/createAccount", "/submit-password", "/products", "/assets/**")
						.permitAll() // URLs that don't require authentication.
						// .requestMatchers("/createAccount").hasRole("ADMIN")
						// .requestMatchers("/cart/**").hasRole("USER")
						.anyRequest().authenticated() // All other URLs require authentication.
				)

				.formLogin(formLogin -> formLogin.loginPage("/login") // Specify the login page URL.
						.loginProcessingUrl("/login") // Specify the URL to process login requests.
						.permitAll() // Allow everyone to access the login page.
						.successHandler(successHandler) // Custom success handler for login.
						.failureUrl("/login?error=true"))

				.logout(logout -> logout.permitAll() // Allow everyone to log out.
				)

				// Request cache - remember the last visited page after login.
				.requestCache(requestCache -> requestCache.requestCache(new HttpSessionRequestCache())
				// Use HttpSessionRequestCache to store the last visited URL.

				);

		// Return the SecurityFilterChain configuration.
		return httpSecurity.build();
	}

	// connect user table to spring security, for that we are using customer
	// (MyUserDetailService) class which implements
	// default UserDetailsService Interface
	// it tells spring how to retrieve user data
	// Without this, Spring can’t authenticate users from a database.
	@Bean
	UserDetailsService userDetailService() {
		return myUserDetailService;
	}

//	It's the core engine that verifies user credentials.
//
//	If this is not defined, Spring won’t know how to authenticate users from the DB properly.

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		System.out.println("provider");
		provider.setUserDetailsService(myUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

//	Compare raw input password with the hashed one in the DB (during login)
// BCrypt is one of the most safest encoder
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// SpringSecurityDialect is a thymeleaf dialect that allows you to use spring
	// security expressions directly
	// into your HTML templates..in order to enable it, we have to register this as
	// a bean
	@Bean
	SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
}