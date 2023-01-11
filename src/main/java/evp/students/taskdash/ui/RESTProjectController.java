package evp.students.taskdash.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import evp.students.taskdash.domain.Project;
import evp.students.taskdash.domain.Task;
import evp.students.taskdash.domain.User;
import evp.students.taskdash.services.AuthService;
import evp.students.taskdash.services.ProjectService;
import evp.students.taskdash.services.UserService;





@RestController
@RequestMapping("/api/projects")
public class RESTProjectController {
	
	private final String KEY = "123456"; 

	@Autowired
	private ProjectService projServ;
	
	
	
    @GetMapping("")
    public ResponseEntity<Object> projectIndex(
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "2") int size,
    		@RequestParam(defaultValue = "") String key
    		) {
    	
    	if(key.equals(KEY)) {
    		Pageable pageable = PageRequest.of(page,size);
        	Page<Project> pageOfProjects = projServ.findAll(pageable);
       
        	return new ResponseEntity<Object>(pageOfProjects, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<Object>(null, HttpStatus.FORBIDDEN);
    	}
        	
    	
   
    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> projectDetail(
    		@RequestParam(defaultValue = "") String key,
    		@PathVariable Long id
    		) {
    	
    	if(key.equals(KEY)) {
    		Optional<Project> project = projServ.get(id);
        	return new ResponseEntity<Object>(project, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<Object>(null, HttpStatus.FORBIDDEN);
    	}
        	
    	
   
    	
    }
    
   
    
}


