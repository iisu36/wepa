/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class SearchController {

    @Autowired
    private AuthService auth;

    @Autowired
    private ProfileRepository proRep;

    @Autowired
    private ConnectionRepository conRep;
    
    @GetMapping("dry-chamber-49238.herokuapp.com/search/")
    public String herokuSearch(@RequestParam String username, Model model) {

        Profile current = auth.getProfile();

        model.addAttribute("profile", current);

        List<Profile> list = proRep.findAll();

        ArrayList<Profile> profiles = new ArrayList<>();

        for (Profile user : list) {
            if (user.getUsername().toLowerCase().contains(username)) {
                profiles.add(user);
            }
        }

        profiles.remove(current);

        model.addAttribute("profiles", profiles);

        ArrayList<Profile> connections = new ArrayList<>();
        ArrayList<Profile> requests = new ArrayList<>();
        ArrayList<Profile> penders = new ArrayList<>();

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
                    penders.add(connection.getSender());
                } else {
                    penders.add(connection.getReceiver());
                }
            }
        }

        model.addAttribute("connections", connections);
        model.addAttribute("requests", requests);
        model.addAttribute("penders", penders);

        return "search";
    }

    @GetMapping("dry-chamber-49238.herokuapp.com/search/{id}/photo")
    public ResponseEntity<byte[]> herokuViewPhoto(@PathVariable Long id) {
        Profile profile = proRep.getOne(id);
        return new ResponseEntity<>(profile.getPhoto(), HttpStatus.OK);
    }

    @PostMapping("dry-chamber-49238.herokuapp.com/request/{username}")
    public String herokuRequest(@PathVariable String username) {

        Profile profile = proRep.findByUsername(username);
        Profile current = auth.getProfile();

        Connection connection = new Connection();
        connection.setSender(current);
        connection.setReceiver(profile);
        connection.setStatus("Pending");

        conRep.save(connection);

        current.getConnections().add(connection);
        profile.getConnections().add(connection);

        proRep.save(current);
        proRep.save(profile);

        return "redirect://dry-chamber-49238.herokuapp.com/mywall";
    }   

    @GetMapping("/search/")
    public String search(@RequestParam String username, Model model) {

        Profile current = auth.getProfile();

        model.addAttribute("profile", current);

        List<Profile> list = proRep.findAll();

        ArrayList<Profile> profiles = new ArrayList<>();

        for (Profile user : list) {
            if (user.getUsername().toLowerCase().contains(username)) {
                profiles.add(user);
            }
        }

        profiles.remove(current);

        model.addAttribute("profiles", profiles);

        ArrayList<Profile> connections = new ArrayList<>();
        ArrayList<Profile> requests = new ArrayList<>();
        ArrayList<Profile> penders = new ArrayList<>();

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
                    penders.add(connection.getSender());
                } else {
                    penders.add(connection.getReceiver());
                }
            }
        }

        model.addAttribute("connections", connections);
        model.addAttribute("requests", requests);
        model.addAttribute("penders", penders);

        return "search";
    }

    @GetMapping("/search/{id}/photo")
    public ResponseEntity<byte[]> viewPhoto(@PathVariable Long id) {
        Profile profile = proRep.getOne(id);
        return new ResponseEntity<>(profile.getPhoto(), HttpStatus.OK);
    }

    @PostMapping("/request/{username}")
    public String request(@PathVariable String username) {

        Profile profile = proRep.findByUsername(username);
        Profile current = auth.getProfile();

        Connection connection = new Connection();
        connection.setSender(current);
        connection.setReceiver(profile);
        connection.setStatus("Pending");

        conRep.save(connection);

        current.getConnections().add(connection);
        profile.getConnections().add(connection);

        proRep.save(current);
        proRep.save(profile);

        return "redirect://dry-chamber-49238.herokuapp.com/mywall";
    }
}
