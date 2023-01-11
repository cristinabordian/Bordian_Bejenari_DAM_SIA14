package evp.students.taskdash.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
	
	//attributes
	@Id
	@GeneratedValue(generator="increment")
	public Long id;
	
	public String title;
	public String description; 
	public String taskStatus; 
	public String priorityLevel; 
	public LocalDate createdOn; 
	public LocalDate lastUpdate; 
	
	//relationships
	@ManyToOne
	public Project project;
	
	@OneToOne
	public User assignee;
	
	
	public Task  () {}


	public Task(String title, String description, String taskStatus, String priorityLevel) {
		super();
		this.title = title;
		this.description = description;
		this.taskStatus = taskStatus;
		this.priorityLevel = priorityLevel;
		
	}
	
	
	
	// LIFECYCLE of the Entity 
	
		@PrePersist
		protected void onCreate() {
		createdOn = LocalDate.now();
		}

		@PreUpdate
		protected void onUpdate() {
		 lastUpdate = LocalDate.now();
		}
	
	
} 