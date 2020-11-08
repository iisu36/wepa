/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;


import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Iizu
 */
@Controller
public class MyWallController {
    
    @Autowired
    private AuthService auth;
    
    @Autowired
    private ProfileRepository proRep;
    
    @Autowired
    private SkillRepository skiRep;

    @GetMapping("/mywall")
    public String mywall(Model model) {
        
        model.addAttribute("profile", auth.getProfile());
        
        return "mywall";
    }
    
    @PostMapping("/mywall/photo")
    public String addPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Profile profile = auth.getProfile();
        
        profile.setPhoto(file.getBytes());
        
        proRep.save(profile);
        
        return "redirect:/mywall";
    }
    
    @GetMapping("/mywall/photo")
    public ResponseEntity<byte[]> viewPhoto() {
        Profile profile = auth.getProfile();
        return new ResponseEntity<>(profile.getPhoto(), HttpStatus.OK);
    }
    
    @PostMapping("/mywall/skill")
    public String addSkill(@RequestParam String skillToAdd) {
        Profile profile = auth.getProfile();
        
        Skill skill = new Skill();
        skill.setSkill(skillToAdd);
        
        skiRep.save(skill);
        
        profile.getSkills().add(skill);
        
        proRep.save(profile);
        
        return "redirect:/mywall";
    }
    
    
}


