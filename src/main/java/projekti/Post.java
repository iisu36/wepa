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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Profile poster;
    
    private LocalDateTime timestamp;
    
    private String content;
    
    private Integer likes;
    
    @ManyToMany
    private List<Profile> likers = new ArrayList<>();
    
    private Long commentOnId;
}
