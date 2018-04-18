package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService extends UserDetailsService {
	public List<User> findAll();
	public Page<User> findAll(Pageable pageable);
	public List<User> findUsersByEmailLikeAndRoles_RoleOrderByEmailAsc(String email, String role);
	public Page<User> findUsersByEmailLikeAndRoles_RoleOrderByEmailAsc(String email, String role, Pageable pageable);
	public User findUserById(Long id);
	public User findUserByEmail(String email);
	public void save(User user);
	public void resetPassword(User user, String password);
	public void delete(Long id);
}
