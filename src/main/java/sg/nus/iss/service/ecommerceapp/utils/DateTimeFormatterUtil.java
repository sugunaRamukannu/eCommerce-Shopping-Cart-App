package sg.nus.iss.service.ecommerceapp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component("dateTimeFormatterUtil")
public class DateTimeFormatterUtil {
    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return dateTime.format(formatter);
    }
}
