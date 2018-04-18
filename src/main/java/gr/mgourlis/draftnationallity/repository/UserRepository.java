package gr.mgourlis.draftnationallity.repository;

import gr.mgourlis.draftnationallity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 List<User> findUsersByEmailLikeAndRoles_RoleOrderByEmailAsc(String email, String role);
	 Page<User> findUsersByEmailLikeAndRoles_RoleOrderByEmailAsc(String email, String role, Pageable pageable);
}
