package sg.nus.iss.service.ecommerceapp.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
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

        // Default redirect URL
        String redirectUrl = "/";

        // 1. Check if user has ADMIN role
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        System.out.println("adminbefore");
        if (isAdmin) {
        	System.out.println("admin");
        	 redirectUrl = "http://localhost:3000/admin";
        } else {
            // 2. Get the saved request URL for normal users
            SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

            if (savedRequest != null) {
                redirectUrl = savedRequest.getRedirectUrl(); // ✅ Redirect to last visited page
            }
        }

        response.sendRedirect(redirectUrl); // 🔁 Perform the redirect
    }
}