package evp.students.taskdash.ui;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import evp.students.taskdash.domain.User;
import evp.students.taskdash.services.AuthService;

@Controller
public class AuthController {
	@Autowired
	private AuthService authServ;

    @GetMapping("/dash/signin")
    public String dashSignin() {
        return "dash-signin";
    }
    
    
    @GetMapping("/dash/signout")
    public String dashSignout(HttpSession session) {
    	session.removeAttribute("user");
    	session.removeAttribute("id");
    	session.removeAttribute("role");
    	return "redirect:/dash/signin";
    }
    
    @PostMapping("/dash/signin") //dependency injection!!!!! due to annotations
    public String signin(
    		@ModelAttribute("username") String username, 
    		@ModelAttribute("password") String password,
    	    RedirectAttributes redirectAttrs,
    	    HttpSession session) {
    

    	
    	User user = authServ.signin(username, password);
    	
    	if (user != null) {
    		session.setAttribute("user", user.username);
    		session.setAttribute("id", user.id);
    		if (authServ.hasRole(user.id, "admin")) {
    			session.setAttribute("role", "admin");
        	} 
    		return "redirect:/dash/index";
    	} else {
    		redirectAttrs.addFlashAttribute("message", "Incorrect credentials!");
    		return "redirect:/dash/signin";
    	}
   
        
    }
    
    
    
    /////
    @GetMapping("/dash/signup")
    public String dashSignup() {
        return "dash-signup";
    }
    
    
    
    @PostMapping("/dash/signup") //dependency injection!!!!! due to annotations
    public String signup(
    		@ModelAttribute("username") String username, 
    		@ModelAttribute("password") String password,
    		@ModelAttribute("password_confirm") String password_confirm,
    	    RedirectAttributes redirectAttrs,
    	    HttpSession session) {
    
       if (password.equals(password_confirm)) {
    	   User user = new User (username, password,"");
    	   authServ.signup(user);
    	   session.setAttribute("user", user.username);
       } else {
    	   redirectAttrs.addFlashAttribute("message", "passwords don't match");
   		return "redirect:/dash/signup";
    	   
       }
    	
    	
    	
            return "redirect:/dash/index";
   
        
    }
    
}
