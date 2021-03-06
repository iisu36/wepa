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
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends AbstractPersistable<Long> {
    
    private Long accountId;
    
    private String username;
    
    @Lob
    //@Basic(fetch = FetchType.LAZY)
    private byte[] photo;
    
    @ManyToMany
    private List<Connection> connections = new ArrayList<>();
    
    @OneToMany
    private List<Skill> skills = new ArrayList<>();
    
    @OneToMany(mappedBy = "poster")
    private List<Post> posts = new ArrayList<>();
}
