/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Iizu
 */
@Controller
public class MyWallController {
    
    @Autowired
    private AuthService auth;

    @GetMapping("/mywall")
    public String mywall(Model model) {
        
        model.addAttribute("account", auth.getAccount());
        
        return "mywall";
    }
}
