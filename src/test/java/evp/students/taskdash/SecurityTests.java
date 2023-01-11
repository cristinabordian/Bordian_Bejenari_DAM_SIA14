package evp.students.taskdash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import evp.students.taskdash.domain.User;
import evp.students.taskdash.services.AuthService;
import evp.students.taskdash.services.UserService;

@SpringBootTest
class SecurityTests {
	
	@Autowired
	private AuthService authServ;
	@Autowired
	private UserService userServ;
	
	@Test
	void signup() {
		// set up
		String username = "testuser1";
		String password = "123456";
		
		User user = new User(username, password,"");
 	    authServ.signup(user);
 	    
 	    // assertion
 	    User userDb = userServ.get(user.id).get();
 	    assertEquals(userDb.username, user.username);
 	    assertEquals(userDb.password, user.password);
 	    
 	    // cleanup
 	    userServ.delete(user.id);
	}
	
	

}
