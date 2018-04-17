package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.User;
import gr.mgourlis.draftnationallity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long id) {
		 return userRepository.getOne(id);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return user;
	}


}
