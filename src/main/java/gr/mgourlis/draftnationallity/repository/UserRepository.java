package gr.mgourlis.draftnationallity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.mgourlis.draftnationallity.model.User;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}
