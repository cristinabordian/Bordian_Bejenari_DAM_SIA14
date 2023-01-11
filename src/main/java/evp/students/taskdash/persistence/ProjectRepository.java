package evp.students.taskdash.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import evp.students.taskdash.domain.Project;





public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	

}