package sg.nus.iss.service.ecommerceapp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component("dateTimeFormatterUtil")
public class DateTimeFormatterUtil {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

	public String formatDate(LocalDateTime dateTime) {
		return dateTime.format(formatter);

	}
}
