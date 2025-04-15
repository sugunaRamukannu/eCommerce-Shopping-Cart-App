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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

<<<<<<< Updated upstream
=======
import jakarta.servlet.http.HttpServletResponse;
>>>>>>> Stashed changes

@Configuration
@EnableWebSecurity // to enable spring security and integrate with our app
public class Webconfig { // custom config class to define security behaviour

	@Autowired
	private MyUserDetailService myUserDetailService; // class to load user details from the database (need only for
	// custom login features)

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;// custom login success handler defines what happens after
																// a user logs in successfully

	@Bean
<<<<<<< Updated upstream
	public SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
		// Disable CSRF if you're not dealing with a stateful application (like RESTful
		// APIs).
		httpSecurity
		.cors(Customizer.withDefaults()) 
		.csrf(csrf -> csrf.disable()) 
				// Authorization configuration - define which URLs are accessible by which
				// roles.
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/login-check", "/forgot-password", "/register","/search",
//								"/cart","/purchases",
								"/send-otp","/api/products","/reset-password", "/createAccount", "/submit-password", "/api/products/**",
								"/assets/**")
						.permitAll() // URLs that don't require authentication.
						 .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()  // Allow fetching
				            .requestMatchers(HttpMethod.DELETE, "/api/products/**").permitAll()  // Allow delete
				            .requestMatchers(HttpMethod.PUT, "/api/products/**").permitAll()//allow update
				            .requestMatchers(HttpMethod.POST, "/api/products/**").permitAll()//allow update
				            .requestMatchers("/api/**").permitAll()
						// .requestMatchers("/createAccount").hasRole("ADMIN")
						// .requestMatchers("/cart/**").hasRole("USER")
=======
	SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		// the main configuration point for securing HTTP requests.
		http

				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth
						// Allows anyone to access these public URLs
						.requestMatchers("/", "/login", "/login-check", "/search", "/forgot-password", "/register",
								"/send-otp", "/reset-password", "/createAccount", "/submit-password", "/products",
								"/assets/**")
						.permitAll()
						.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() // Allow
						.requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()																				// fetching
						.requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN") // Allow delete
						.requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")// allow update
						.requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")// allow update
//						.requestMatchers("/api/**").hasRole("ADMIN")// only allow the user who has admin role//same
																	// applied for above four
>>>>>>> Stashed changes
						.anyRequest().authenticated() // All other URLs require authentication.
				)
				

				.exceptionHandling(ex -> ex
					    .authenticationEntryPoint((request, response, authException) -> {
					        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					        response.setContentType("application/json");
					        response.getWriter().write("{\"error\": \"Unauthorized\"}");
					    })
					)
				.formLogin(form -> form.loginPage("/login") // shows the custom login page
						.loginProcessingUrl("/login") // form submission will be taken care in this line and spring
														// security
						// will take of authentication behind the scenes
						.successHandler(successHandler) // Redirect logic defined here after successfull
														// login(customized logic for successhandler)
						.failureUrl("/login?error=true")// if invalid credentials, it will reload the same page with
														// this URL
						.permitAll())
				// Clears session and authentication on logout.
				.logout(logout -> logout.logoutUrl("/logout").invalidateHttpSession(true).clearAuthentication(true)
						.deleteCookies("JSESSIONID") // It will clear the session cookie (JSESSIONID) and make sure the
														// user is fully logged out.
						.permitAll())

				// Remembers the original URL the user wanted before login and redirects to it
				// after successful login
				.requestCache(requestCache -> requestCache.requestCache(new HttpSessionRequestCache()))

<<<<<<< Updated upstream
				// Request cache - remember the last visited page after login.
				.requestCache(requestCache -> requestCache.requestCache(new HttpSessionRequestCache())
						
				// Use HttpSessionRequestCache to store the last visited URL.

				)
		.httpBasic(Customizer.withDefaults()); // ← ADD THIS LINE


		// Return the SecurityFilterChain configuration.
		return httpSecurity.build();
=======
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.invalidSessionUrl("/login?expired=true"));

		return http.build();
>>>>>>> Stashed changes
	}

	@Bean
	UserDetailsService userDetailService() {
		return myUserDetailService;
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(myUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
<<<<<<< Updated upstream
	//SpringSecurityDialect is a thymeleaf dialect that allows you to use spring security expressions directly
	//into your HTML templates..in order to enable it, we have to register this as a bean
=======

>>>>>>> Stashed changes
	@Bean
	 SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean
	 CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React app
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("*"));
	    configuration.setAllowCredentials(true); // optional but useful for session cookies

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React app
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true); 

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
