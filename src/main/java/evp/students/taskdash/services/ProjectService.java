package evp.students.taskdash.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import evp.students.taskdash.domain.Project;
import evp.students.taskdash.domain.Task;
import evp.students.taskdash.persistence.ProjectRepository;

@Service
public class ProjectService {
     @Autowired  
	private ProjectRepository projectRepo;
	
     public Page<Project> findAll(Pageable pageable) {
 		return projectRepo.findAll(pageable);
 	}
 	
	
	public Project save(Project project) {
		return projectRepo.save(project); 
	}
	
	public Optional <Project> get(Long id) {
		return projectRepo.findById(id); 
	}
	
	public void delete(Long id) {
		 projectRepo.deleteById(id); 
	}
	
	public void deleteTask(Long projectId, Long id) {
		 Project project = projectRepo.findById(projectId).get();
		 Task task = project.tasks.stream().filter(t->t.id == id).findFirst().get();
		 project.tasks.remove(task);
		 task.project = null;
		 
		 
		 projectRepo.save(project); 
	}

}
