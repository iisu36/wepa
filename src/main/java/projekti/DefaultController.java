package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String helloWorld(Model model) {
        return "home";
    }
    
    /*@GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    
    @GetMapping("/create-user")
    public String createUser() {
        return "create-user";
    }*/
}
