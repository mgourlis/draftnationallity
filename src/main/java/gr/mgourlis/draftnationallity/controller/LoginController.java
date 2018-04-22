package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.dto.ChangePasswordDTO;
import gr.mgourlis.draftnationallity.model.User;
import gr.mgourlis.draftnationallity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", defaultValue="ok") String error){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("error",error);
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
/*
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("newUser");
		return modelAndView;
	}


	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("newUser");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("newUser");
			
		}
		return modelAndView;
	}
*/

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@RequestMapping(value="/resetpass", method = RequestMethod.GET)
	public ModelAndView resetPassword(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if(user == null){
			throw new EntityNotFoundException();
		}
		modelAndView = new ModelAndView();
		modelAndView.addObject("email",user.getEmail());
		modelAndView.addObject("resetpass",new ChangePasswordDTO());
		modelAndView.setViewName("/resetPassword");
		return modelAndView;
	}


	@RequestMapping(value="/resetpass", method = RequestMethod.POST)
	public ModelAndView setResetPassword(@Valid @ModelAttribute("resetpass") ChangePasswordDTO resetpass, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if (user == null) {
			bindingResult
					.rejectValue("oldPassword", "error.oldPassword",
							"You don't have permission to change this password");
		}
		if(!bCryptPasswordEncoder.matches(resetpass.getOldPassword(),user.getPassword())){
			bindingResult
					.rejectValue("oldPassword", "error.oldPassword",
							"The current password does not match");
		}
		if(!resetpass.getPassword().equals(resetpass.getPasswordConfirm())){
			bindingResult
					.rejectValue("password", "error.password",
							"The passwords does not match");
			bindingResult
					.rejectValue("passwordConfirm", "error.passwordConfirm",
							"The passwords does not match");
		}
		if(!bindingResult.hasErrors()){
			userService.resetPassword(user.getId(),resetpass.getPassword());
			SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
			modelAndView.setViewName("redirect:/login");
		}else {
			modelAndView.setViewName("resetPassword");
		}
		return modelAndView;
	}
	

}
