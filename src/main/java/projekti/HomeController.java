/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author iisakki
 */
@Controller
public class HomeController {
    
    @Autowired
    private AuthService auth;
    
    @GetMapping("dry-chamber-49238.herokuapp.com/")
    public String herokuhome(Model model) {
        model.addAttribute("profile", auth.getProfile());
        return "home";
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("profile", auth.getProfile());
        return "home";
    }
}
