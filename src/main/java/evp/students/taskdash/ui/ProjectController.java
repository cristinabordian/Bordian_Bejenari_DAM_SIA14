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
public class ProjectController {
	

	@Autowired
	private ProjectService projServ;
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private AuthService authServ;
	
    @GetMapping(value={ "/dash/index", "/" })
    public String projectIndex(
    		Model model,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "2") int size,
    		HttpSession session
    		) {
    	
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	} 
    	
    	
    	Pageable pageable = PageRequest.of(page,size);
    	Page<Project> pageOfProjects = projServ.findAll(pageable);
   
    	model.addAttribute("projects", pageOfProjects);
        return "project-index";
    }

    
    @GetMapping("/dash/project/{id}")
    public String projectDetails(@PathVariable Long id, Model model, HttpSession session) {
    	
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	
    	Optional<Project> project = projServ.get(id);
    	// HW1:
    	//    using project to task relationship
    	//    project.tasks
    	model.addAttribute("project", project.get()); // project + tasks
        return "project-details";
    }
    
    @GetMapping("/dash/project/delete/{id}")
    public String projectDelete(@PathVariable Long id, HttpSession session) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	if (session.getAttribute("role").equals("admin")) {
    		projServ.delete(id);
    	} 
    	return "redirect:/dash/index";
    }    
    
    @GetMapping("/dash/project/edit/{id}")
    public String projectEdit(@PathVariable Long id, Model model, HttpSession session) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	Optional<Project> project = projServ.get(id);
    	model.addAttribute("project", project.get());
    	return "project-edit-form";
    }    
    
    @GetMapping("/dash/project/create")
    public String projectCreate(Model model, HttpSession session) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
        return "project-create-form";
    }
    
    
    @PostMapping("/dash/project/edit/{id}")
    // dependency injection
    public String projectUpdate(
    		@ModelAttribute("name") String name,
    		@ModelAttribute("description") String description,
    		@ModelAttribute("dueDate") String dueDate,
    		@PathVariable Long id,
    		RedirectAttributes redirectAttrs,
    		HttpSession session
		) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	// validation
    	Project project = projServ.get(id).get();
    	project.name = name;
    	project.description = description;
    	project.dueDate = LocalDate.parse(dueDate);
    	projServ.save(project);
    	
	  	return "redirect:/dash/project/" + project.id;
    }
    
    
    @PostMapping("/dash/project/create")
    // dependency injection
    public String projectSave(
    		@ModelAttribute("name") String name,
    		@ModelAttribute("description") String description,
    		@ModelAttribute("dueDate") String dueDate,
    		RedirectAttributes redirectAttrs, 
    		HttpSession session
		) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	// validation
    	Project project = new Project(name,description,LocalDate.parse(dueDate));
    	projServ.save(project);
    	

    	return "redirect:/dash/index";
    }
    

    // TASK ROUTES
    @GetMapping("/dash/task/create/{id}")
    public String taskCreate(Model model, @PathVariable Long id, HttpSession session) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	List<User> users = userServ.findAll();
    	model.addAttribute("id", id);
    	model.addAttribute("users", users);
        return "task-create-form";
    }
    
    
    @PostMapping("/dash/task/create/{id}")
    // dependency injection
    public String taskSave(
    		@ModelAttribute("title") String title,
    		@ModelAttribute("description") String description,
    		@ModelAttribute("taskStatus") String taskStatus,
    		@ModelAttribute("priorityLevel") String priorityLevel,
    		@ModelAttribute("assignee") Long assigneeId,

    		RedirectAttributes redirectAttrs,
    		HttpSession session,
    		@PathVariable Long id
		) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	// validation
    	Task task = new Task(title,description,taskStatus, priorityLevel);
    	Project project = projServ.get(id).get();
    	task.project = project;
    	
    	//relation to assignee
    	User assignee = userServ.get(assigneeId).get();
    	task.assignee = assignee;
    	assignee.tasks.add(task);
    	projServ.save(project);
    
    	

    	return "redirect:/dash/project/" + project.id;
    }
    
    
    @GetMapping("/dash/project/task/delete/{projectId}/{id}")
    public String taskDelete(
    		@PathVariable Long projectId,
    		@PathVariable Long id, HttpSession session) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	if (session.getAttribute("role").equals("admin")) {
    		projServ.deleteTask(projectId,id);
    	} 
    	return "redirect:/dash/project/" + projectId;
    }    
    
    @GetMapping("/dash/project/task/edit/{projectId}/{id}")
    public String taskEdit(
    		Model model,
    		@PathVariable Long projectId,
    		@PathVariable Long id, HttpSession session) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	
    	Project project = projServ.get(projectId).get();
    	Task task = project.tasks.stream().filter(t->t.id == id).findFirst().get();
    	List<User> users = userServ.findAll();
    	model.addAttribute("users", users);
    	model.addAttribute("project", project);
    	model.addAttribute("task", task);
    	return "task-edit-form";
    	
    }   
    
    @PostMapping("/dash/project/task/edit/{projectId}/{id}")
    // dependency injection
    public String taskUpdate(
    		@ModelAttribute("title") String title,
    		@ModelAttribute("description") String description,
    		@ModelAttribute("taskStatus") String taskStatus,
    		@ModelAttribute("priorityLevel") String priorityLevel,
    		@ModelAttribute("assignee") Long assigneeId,

    		RedirectAttributes redirectAttrs,
    		HttpSession session,
    		@PathVariable Long projectId,
    		@PathVariable Long id
		) {
    	if (session.getAttribute("user") == null) {
    		return "redirect:/dash/signin";
    	}
    	// validation
    	
    	Project project = projServ.get(id).get();
    	Task task = project.tasks.stream().filter(t->t.id == id).findFirst().get();
    	task.title = title;
    	task.description = description;
    	task.taskStatus = taskStatus;
    	task.priorityLevel = priorityLevel;
       	//relation to assignee
    	User assignee = userServ.get(assigneeId).get();
    	task.assignee = assignee;
    	projServ.save(project);
    	
    	

      
    	

    	return "redirect:/dash/project/" + project.id;
    }
    
}


