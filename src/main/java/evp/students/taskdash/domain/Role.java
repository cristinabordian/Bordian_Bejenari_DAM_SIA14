package evp.students.taskdash.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="roles")
public class Role {
	
	//attributes
	@Id
	@GeneratedValue(generator="increment")
	public Integer id;
	public String name;
	public String description; 
	
	//relationships
    @ManyToMany(mappedBy = "userRoles")
    public List<User> users = new ArrayList<>();
    
   

	
}