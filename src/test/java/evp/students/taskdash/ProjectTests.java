package evp.students.taskdash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import evp.students.taskdash.domain.Project;
import evp.students.taskdash.domain.Task;
import evp.students.taskdash.services.ProjectService;

@SpringBootTest
class ProjectTests {
	
	@Autowired
	private ProjectService projServ;
	
	
	@Test
	@DisplayName("Testing cascading PROJECT -> TASK")
	void testProjectTaskRelationshipCascading() {
		// set up
		Project project = new Project("Test Project 1", "",null);
		
		projServ.save(project);
		
		Task task1 = new Task("Test task 1","","","");
		Task task2 = new Task("Test task 1","","","");
		
		project.tasks.add(task1);
		project.tasks.add(task2);
		
		task1.project = project;
		task2.project = project;
		
		
		
		projServ.save(project);
		
 	    // assertion && cleanup
		Project projectDb = projServ.get(project.id).get();
	
		Task task1Db = projectDb.tasks.get(0);
		Task task2Db = projectDb.tasks.get(1);
		
		
		
		assertEquals(projectDb.id,project.id);
 	    assertNotNull(task1Db);
 	    assertNotNull(task2Db);
 	    
 	    projServ.delete(project.id);
 	    
 	    Optional<Project> projectDbaftDel = projServ.get(project.id);
 	    assertEquals(projectDbaftDel.isEmpty(),true);
 	     
	}
	
	

}
