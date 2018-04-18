package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.model.User;
import gr.mgourlis.draftnationallity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@Secured("ADMIN")
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView showUsers(ModelAndView modelAndView, Pageable pageable){
        Page<User> usersPage = userService.findAll(pageable);
        PageWrapper<User> page = new PageWrapper<User>(usersPage,"/admin/user/");
        modelAndView.addObject("users", page.getContent());
        modelAndView.addObject("page",usersPage);
        modelAndView.setViewName("admin/user/showUsers");
        return modelAndView;
    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public ModelAndView searchUsers(@RequestParam("user") String user, @RequestParam("role") String role){
        ModelAndView modelAndView = new ModelAndView();
        user = user == null ? "" : user;
        role = role == null ? "" : "ROLE_" + role;
        List<User> users = userService.findUsersByEmailLikeAndRoles_RoleOrderByEmailAsc(user,role);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/user/showUsers");
        return modelAndView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ModelAndView showUser(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserById(id);
        try {
            user.setPassword("");
            modelAndView.addObject("users", user);
            modelAndView.setViewName("admin/user/showUser");
            return modelAndView;
        }catch (EntityNotFoundException e){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserById(id);
        if(user == null){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        user.setPassword("");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/admin/user/editUser");
        return modelAndView;
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editUser(@PathVariable("id") long id, @Valid User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        User edituser = userService.findUserById(id);
        if(user == null){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        else if (bindingResult.hasErrors()){
            modelAndView.setViewName("/admin/user/editUser");
        }
        else{
            edituser.setActive(user.isActive());
            edituser.setName(user.getName());
            edituser.setLastName(user.getLastName());
            edituser.setRoles(user.getRoles());
            userService.save(edituser);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", edituser);
        }
        modelAndView.setViewName("redirect:/"+edituser.getId().toString());
        return modelAndView;
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public ModelAndView newUser(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/user/newUser");
        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/user/newUser");
        } else {
            userService.save(user);
            modelAndView.addObject("successMessage", "User has been created successfully");
            modelAndView.addObject("user", user);
            modelAndView.setViewName("redirect:showUser");

        }
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        userService.delete(id);
        modelAndView.setViewName("redirect:/admin/user/");
        return modelAndView;
    }

}
