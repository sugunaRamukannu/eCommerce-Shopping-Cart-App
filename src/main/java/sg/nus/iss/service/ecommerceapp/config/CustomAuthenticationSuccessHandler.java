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

        // Retrieve the saved request (the URL the user was trying to access before login)
        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

        System.out.println("savedRequets"+savedRequest);
        String redirectUrl = "/"; // Default redirect URL if no saved request exists

        // Check if there is a saved request (i.e., the user was trying to access a protected page)
        if (savedRequest != null) {
            redirectUrl = savedRequest.getRedirectUrl(); // Get the last visited page URL
        }

        response.sendRedirect(redirectUrl); // Redirect to the last visited page
    }
}