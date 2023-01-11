package evp.students.taskdash.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evp.students.taskdash.domain.User;
import evp.students.taskdash.persistence.UserRepository;

@Service
public class AuthService {
    @Autowired  
	private UserRepository userRepo;
	
	public User signin(String username, String password) {
       return userRepo.findByUsernameAndPassword(username,password);
    }
	
	
	public User signup(User user) {
	   return userRepo.save(user);
  	}
       
	public Boolean hasRole(Long userId, String roleName) {
		User user = userRepo.findById(userId).get();
		return user.userRoles.stream().filter(role -> role.name.equals(roleName)).count() == 1;
	}
	
}
