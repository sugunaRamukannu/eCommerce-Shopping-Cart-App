package sg.nus.iss.service.ecommerceapp.exception;

//Author(s): Ramukannu Suguna

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}