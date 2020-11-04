/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Iizu
 */

@Service
public class AuthService {

    @Autowired
    private AccountRepository accRep;

    @Autowired
    private ProfileRepository proRep;
    
    public Profile getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = accRep.findByUsername(auth.getName());
        
        return proRep.findByAccountId(account.getId());
    }
}
