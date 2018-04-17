package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
	public List<User> findAll();
	public User findUserById(Long id);
	public User findUserByEmail(String email);
	public void save(User user);
}
