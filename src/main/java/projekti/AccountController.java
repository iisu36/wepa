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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    public String list(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accounts";
    }

    @PostMapping("/accounts")
    public String add(@RequestParam String username, @RequestParam String password) {
        if (accountRepository.findByUsername(username) != null) {
            return "redirect:/accounts";
        }
        List<String> auth = new ArrayList<>();
        auth.add("LINKER");
        Account a = new Account(username, passwordEncoder.encode(password), auth);
        accountRepository.save(a);
        return "redirect:/accounts";
    }

}