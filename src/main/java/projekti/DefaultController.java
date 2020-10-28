package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("dry-chamber-49238.herokuapp.com/")
    public String herokuhome(Model model) {
        return "fragments/home";
    }
    
    @GetMapping("dry-chamber-49238.herokuapp.com/login")
    public String herokulogin() {
        return "login";
    }
    
    @GetMapping("/")
    public String home(Model model) {
        return "fragments/demo";
    }
    
    @GetMapping("/login")
    public String login() {      
        return "login";
    }
}