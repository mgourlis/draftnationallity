package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
	public List<User> findAll();
	public User findUserById(int id);
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
