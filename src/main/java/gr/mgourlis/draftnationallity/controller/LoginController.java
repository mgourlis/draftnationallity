package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.model.User;
import gr.mgourlis.draftnationallity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value="/resetpass")
	public ModelAndView resetPassword(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user",user);
		modelAndView.addObject("oldPassword","");
		modelAndView.addObject("password","");
		modelAndView.addObject("passwordConfirm","");
		modelAndView.setViewName("/resetPassword");
		return modelAndView;
	}


	@RequestMapping(value="/resetpass", method = RequestMethod.POST)
	public ModelAndView setResetPassword(@RequestParam(value="oldPassword", defaultValue = "") String oldPassword,
										 @RequestParam(value="password", defaultValue = "") String password,
										 @RequestParam(value="passwordConfirm", defaultValue = "") String passwordConfirm,
                                         User postuser, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if (user == null) {
			bindingResult
					.rejectValue("massageBox", "error.message",
							"You don't have permission");
		}
		if(!user.getPassword().equals(bCryptPasswordEncoder.encode(oldPassword))){
			bindingResult
					.rejectValue("oldPassword", "error.oldPassword",
							"The old password does not match");
		}
		if(!password.equals(passwordConfirm)){
			bindingResult
					.rejectValue("passwordConfirm", "error.passwordConfirm",
							"The password confirmation does not match");
		}
		if(!bindingResult.hasErrors()){
			userService.resetPassword(user.getId(),password);
			SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
			modelAndView.setViewName("redirect:/login");
		}else {
		    modelAndView.addObject("user",postuser);
			modelAndView.setViewName("/resetpass");
		}
		return modelAndView;
	}
	

}
