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
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository  extends JpaRepository<Connection, Long> {
    Connection findBySender(Profile sender);
    Connection findByReceiver(Profile receiver);
    Connection findBySenderAndReceiver(Profile sender, Profile receiver);
}
