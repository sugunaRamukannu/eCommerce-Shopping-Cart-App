package sg.nus.iss.service.ecommerceapp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

//Author(s): Ramukannu Suguna

@Configuration
@EnableWebSecurity
public class Webconfig {

	@Autowired
	private MyUserDetailService myUserDetailService;

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Bean
	 SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
	
		httpSecurity
		.cors(Customizer.withDefaults()) 
		.csrf(csrf -> csrf.disable()) 
				// Authorization configuration - define which URLs are accessible by which
				// roles.
				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/", "/login", "/login-check", "/forgot-password", "/register","/search", "/products/**", "/terms",

								"/send-otp","/api/products","/reset-password", "/createAccount", "/submit-password", "/api/products/**",
								"/assets/**",
								"/app/**", // Allow React routes
								"/app/static/**",     // JS, CSS, media
								"/app/manifest.json", // Manifest
								"/app/logo*.png",     // Icons
								"/app/favicon.ico")   // Favicon
						.permitAll() // URLs that don't require authentication.
						 .requestMatchers(HttpMethod.GET, "/api/products/**").hasRole("ADMIN") 
						 .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
				            .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN") 
				            .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
				            .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN") 
				            .requestMatchers("/api/**").hasRole("ADMIN")
						
						.anyRequest().authenticated() // All other URLs require authentication.
				)
				

				.formLogin(formLogin -> formLogin.loginPage("/login") // Specify the login page URL.
						.loginProcessingUrl("/login") // Specify the URL to process login requests.
						.permitAll() // Allow everyone to access the login page.
						.successHandler(successHandler) // Custom success handler for login.
						.failureUrl("/login?error=true"))

				.logout(logout -> logout
					    .logoutUrl("/logout")
					    .logoutSuccessUrl("/") // Redirect here after logout
					    .invalidateHttpSession(true)
					    .deleteCookies("JSESSIONID")
					    .permitAll()
					)
				.sessionManagement(session -> session
				    .invalidSessionUrl("/")
				 // Allows everyone to log out.
				)

				// Request cache - remember the last visited page after login.
				.requestCache(requestCache -> requestCache.requestCache(new HttpSessionRequestCache())
						
				// Use HttpSessionRequestCache to store the last visited URL.

				)
		.httpBasic(Customizer.withDefaults()); // ← ADD THIS LINE



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

//	If this is not defined, Spring won’t know how to authenticate users from the DB properly.

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	
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
	//SpringSecurityDialect is a thymeleaf dialect that allows you to use spring security expressions directly
	//into your HTML templates..in order to enable it, we have to register this as a bean
	@Bean
	 SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean
	 CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
//	    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React app
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("*"));
	    configuration.setAllowCredentials(true); // optional but useful for session cookies

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
}