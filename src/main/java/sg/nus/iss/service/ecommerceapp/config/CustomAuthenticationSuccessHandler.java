package sg.nus.iss.service.ecommerceapp.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {

	    // Check user roles
	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	    boolean isAdmin = authorities.stream()
	        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

	    if (isAdmin) {
	        // If admin, redirect to /api/products
	        response.sendRedirect("/admin/products");
	    } else {
	        // If not admin, check for saved request (i.e., the page they originally wanted to access)
	        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

	        String redirectUrl = "/"; // default fallback
	        if (savedRequest != null) {
	            redirectUrl = savedRequest.getRedirectUrl();
	        }

	        response.sendRedirect(redirectUrl);
	    }
	}

}