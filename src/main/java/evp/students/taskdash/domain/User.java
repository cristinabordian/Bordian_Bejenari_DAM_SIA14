package evp.students.taskdash.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;





@Entity
@Table(name="users")
public class User {
	
	//attributes
	@Id
	@GeneratedValue(generator="increment")
    public Long id;
	public String username; 
    @Column(length = 80)
    public String password;
    public String emailAddress;
    public LocalDate createdOn; 
	public LocalDate lastUpdate;
    
	
    public User () {}; 
    public User(String username, String password, String emailAddress) {
		super();
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
	}

	//relationships
    
  
    
    @ManyToMany()
    public List<Role> userRoles = new ArrayList<>();
    
    @OneToMany(mappedBy="assignee", cascade = CascadeType.ALL)
    public List <Task> tasks = new ArrayList<>();
    
    
    
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
