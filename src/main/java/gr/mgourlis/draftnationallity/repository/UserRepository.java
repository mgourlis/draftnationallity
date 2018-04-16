package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}
