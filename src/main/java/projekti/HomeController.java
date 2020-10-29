/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author iisakki
 */
@Controller
public class HomeController {
    
    @GetMapping("dry-chamber-49238.herokuapp.com/")
    public String herokuhome(Model model) {
        return "home";
    }
    
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}
