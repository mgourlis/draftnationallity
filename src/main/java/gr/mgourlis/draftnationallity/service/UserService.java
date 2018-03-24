package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
