package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
    
    @GetMapping("dry-chamber-49238.herokuapp.com/login")
    public String herokulogin() {
        return "login";
    }
    
    @GetMapping("/login")
    public String login() {      
        return "login";
    }
}