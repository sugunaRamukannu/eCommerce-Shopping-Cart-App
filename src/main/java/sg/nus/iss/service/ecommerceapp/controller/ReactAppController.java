package sg.nus.iss.service.ecommerceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReactAppController {
@GetMapping(value = "/app/{path:[^\\.]*}")
    public String forwardGetReactApp() {
        return "forward:/app/index.html";
    }
	
	@PostMapping(value = "/app/{path:[^\\.]*}")
    public String forwardPostReactApp() {
        return "forward:/app/index.html";
    }
}
