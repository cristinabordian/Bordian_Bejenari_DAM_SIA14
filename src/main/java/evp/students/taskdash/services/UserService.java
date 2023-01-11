package evp.students.taskdash.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import evp.students.taskdash.domain.Project;
import evp.students.taskdash.domain.User;
import evp.students.taskdash.persistence.UserRepository;

@Service
public class UserService {
     @Autowired  
	private UserRepository userRepo;
	
     public List<User> findAll() {
  		return userRepo.findAll();
  	}
     public Page<User> findAll(Pageable pageable) {
  		return userRepo.findAll(pageable);
  	}
  	
     
     public Optional <User> get(Long id) {
 		return userRepo.findById(id); 
 	}
	
     
    public void delete(Long id) {
		 userRepo.deleteById(id); 
	}
    
    
    
       
}
