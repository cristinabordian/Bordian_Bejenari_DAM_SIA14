package evp.students.taskdash.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import evp.students.taskdash.domain.User;



public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsernameAndPassword(String username, String password);

	
}
