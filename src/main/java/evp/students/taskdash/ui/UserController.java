package evp.students.taskdash.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import evp.students.taskdash.domain.Project;
import evp.students.taskdash.domain.Task;
import evp.students.taskdash.domain.User;
import evp.students.taskdash.services.AuthService;
import evp.students.taskdash.services.ProjectService;
import evp.students.taskdash.services.UserService;





@Controller
public class UserController {
	

	@Autowired
	private ProjectService projServ;

	@Autowired
	private AuthService authServ;
	
	@Autowired
	private UserService userServ;
	
    @GetMapping(value={ "/dash/users" })
    public String userIndex(
    		Model model,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "5") int size,
    		HttpSession session
    		) {
    	
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	} 
    	
    	
    	Pageable pageable = PageRequest.of(page,size);
    	Page<User> pageOfUsers = userServ.findAll(pageable);
   
    	model.addAttribute("users", pageOfUsers);
        return "user-index";
    }

    
    @GetMapping("/dash/user/delete/{id}")
    public String userDelete(@PathVariable Long id, HttpSession session) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	if (authServ.hasRole(Long.parseLong(session.getAttribute("id").toString()), "admin")) {
    		userServ.delete(id);
    	} 
    	return "redirect:/dash/users";
    }    
    
}


