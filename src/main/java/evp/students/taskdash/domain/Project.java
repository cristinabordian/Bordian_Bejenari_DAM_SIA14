package evp.students.taskdash.domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="projects")
public class Project {
	@Id
	@GeneratedValue(generator="increment")
	public Long id;
	public Project() {};
	public Project(String name, String description, LocalDate localDate) {
		super();
		this.name = name;
		this.description = description;
		this.dueDate = localDate;
	}
	
	public String name;
	public String description; 
	public LocalDate dueDate; 
	public LocalDate createdOn; 
	public LocalDate lastUpdate; 
	
	
	// RELATIONSHIPS
	@OneToMany(
			mappedBy = "project",
			cascade = CascadeType.ALL
	)
	public List<Task> tasks = new ArrayList<>();
	
	
	
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
