package sg.nus.iss.service.ecommerceapp.exception;

//Author(s): Ramukannu Suguna

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}