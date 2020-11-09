/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
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
        return "home";
    }
}
