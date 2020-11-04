/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

/**
 *
 * @author iisakki
 */
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @ModelAttribute
    private Account getAccount() {
        return new Account();
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        
        if(accountRepository.findByUsername(account.getUsername()) != null) {
            model.addAttribute("used", "true");
            return "registration";
        }
        
        List<String> auth = new ArrayList<>();
        auth.add("LINKER");
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAuthorities(auth);
        accountRepository.save(account);
        
        Profile profile = new Profile();
        profile.setUsername(account.getUsername());
        profile.setAccountId(account.getId());
        profileRepository.save(profile);
        
        return "redirect:/registered";
    }
    
    @GetMapping("/registered")
    public String registered() {
        return "registered";
    }

    @GetMapping("dry-chamber-49238.herokuapp.com/registration")
    public String herokuregistration() {
        return "registration";
    }

    @PostMapping("dry-chamber-49238.herokuapp.com/registration")
    public String herokuregister(@Valid @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        
        if(accountRepository.findByUsername(account.getUsername()) != null) {
            model.addAttribute("used", "true");
            return "registration";
        }
        
        List<String> auth = new ArrayList<>();
        auth.add("LINKER");
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAuthorities(auth);
        accountRepository.save(account);
        
        Profile profile = new Profile();
        profile.setUsername(account.getUsername());
        profile.setAccountId(account.getId());
        profileRepository.save(profile);
        
        return "redirect:dry-chamber-49238.herokuapp.com/registered";
    }
    
    @GetMapping("dry-chamber-49238.herokuapp.com/registered")
    public String herokuregistered() {
        return "registered";
    }
}
