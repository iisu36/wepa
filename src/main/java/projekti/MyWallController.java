/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private ConnectionRepository conRep;
    
    @GetMapping("dry-chamber-49238.herokuapp.com/mywall")
    public String herokuMywall(Model model) {

        Profile current = auth.getProfile();

        model.addAttribute("profile", current);

        ArrayList<Profile> connections = new ArrayList<>();
        ArrayList<Profile> requests = new ArrayList<>();

        for (Connection connection : current.getConnections()) {

            if (connection.getStatus().equals("Connected")) {

                if (connection.getReceiver().getUsername().equals(current.getUsername())) {
                    connections.add(connection.getSender());
                } else {
                    connections.add(connection.getReceiver());
                }
            }
            
            if (connection.getStatus().equals("Pending")) {
                
                if (connection.getReceiver().getUsername().equals(current.getUsername())) {
                    requests.add(connection.getSender());
                }
            }
        }

        model.addAttribute("connections", connections);
        model.addAttribute("requests", requests);

        return "mywall";
    }

    @PostMapping("dry-chamber-49238.herokuapp.com/mywall/photo")
    public String herokuAddPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Profile profile = auth.getProfile();

        profile.setPhoto(file.getBytes());

        proRep.save(profile);

        return "redirect:/mywall";
    }

    @GetMapping("dry-chamber-49238.herokuapp.com/mywall/photo")
    public ResponseEntity<byte[]> herokuViewPhoto() {
        Profile profile = auth.getProfile();
        return new ResponseEntity<>(profile.getPhoto(), HttpStatus.OK);
    }

    @PostMapping("dry-chamber-49238.herokuapp.com/mywall/skill")
    public String herokuAddSkill(@RequestParam String skillToAdd) {
        Profile profile = auth.getProfile();

        Skill skill = new Skill();
        skill.setSkill(skillToAdd);

        skiRep.save(skill);

        profile.getSkills().add(skill);

        proRep.save(profile);

        return "redirect:/mywall";
    }
    
    @PostMapping("dry-chamber-49238.herokuapp.com/requests/{username}")
    public String herokuConnect(@PathVariable String username) {
        
        Profile current = auth.getProfile();
        Profile sender = proRep.findByUsername(username);
        
        Connection connection = conRep.findBySenderAndReceiver(sender, current);
        connection.setStatus("Connected");
        
        conRep.save(connection);
        
        for (Connection conn : current.getConnections()) {
            if (conn.getSender().equals(sender)) {
                conn = connection;
                break;
            }
        }
        
        proRep.save(current);
        
        for (Connection con : sender.getConnections()) {
            if (con.getReceiver().equals(current)) {
                con = connection;
                break;
            }
        }
        
        proRep.save(sender);
        
        return "redirect:/mywall";
    }

    @PostMapping("dry-chamber-49238.herokuapp.com/delete/{username}")
    public String herokuDelete(@PathVariable String username) {
        
        Profile current = auth.getProfile();
        Profile sender = proRep.findByUsername(username);
        
        Connection connection = conRep.findBySenderAndReceiver(sender, current);
        
        
        for (Connection conn : current.getConnections()) {
            if (conn.getSender().equals(sender)) {
                current.getConnections().remove(conn);
                break;
            }
        }
        
        proRep.save(current);
        
        for (Connection con : sender.getConnections()) {
            if (con.getReceiver().equals(current)) {
                sender.getConnections().remove(con);
                break;
            }
        }
        
        proRep.save(sender);
        
        conRep.delete(connection);
        
        return "redirect:/mywall";
    }

    @GetMapping("/mywall")
    public String mywall(Model model) {

        Profile current = auth.getProfile();

        model.addAttribute("profile", current);

        ArrayList<Profile> connections = new ArrayList<>();
        ArrayList<Profile> requests = new ArrayList<>();

        for (Connection connection : current.getConnections()) {

            if (connection.getStatus().equals("Connected")) {

                if (connection.getReceiver().getUsername().equals(current.getUsername())) {
                    connections.add(connection.getSender());
                } else {
                    connections.add(connection.getReceiver());
                }
            }
            
            if (connection.getStatus().equals("Pending")) {
                
                if (connection.getReceiver().getUsername().equals(current.getUsername())) {
                    requests.add(connection.getSender());
                }
            }
        }

        model.addAttribute("connections", connections);
        model.addAttribute("requests", requests);

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
    
    @PostMapping("/requests/{username}")
    public String connect(@PathVariable String username) {
        
        Profile current = auth.getProfile();
        Profile sender = proRep.findByUsername(username);
        
        Connection connection = conRep.findBySenderAndReceiver(sender, current);
        connection.setStatus("Connected");
        
        conRep.save(connection);
        
        for (Connection conn : current.getConnections()) {
            if (conn.getSender().equals(sender)) {
                conn = connection;
                break;
            }
        }
        
        proRep.save(current);
        
        for (Connection con : sender.getConnections()) {
            if (con.getReceiver().equals(current)) {
                con = connection;
                break;
            }
        }
        
        proRep.save(sender);
        
        return "redirect:/mywall";
    }

    @PostMapping("/delete/{username}")
    public String delete(@PathVariable String username) {
        
        Profile current = auth.getProfile();
        Profile sender = proRep.findByUsername(username);
        
        Connection connection = conRep.findBySenderAndReceiver(sender, current);
        
        
        for (Connection conn : current.getConnections()) {
            if (conn.getSender().equals(sender)) {
                current.getConnections().remove(conn);
                break;
            }
        }
        
        proRep.save(current);
        
        for (Connection con : sender.getConnections()) {
            if (con.getReceiver().equals(current)) {
                sender.getConnections().remove(con);
                break;
            }
        }
        
        proRep.save(sender);
        
        conRep.delete(connection);
        
        return "redirect:/mywall";
    }
}
