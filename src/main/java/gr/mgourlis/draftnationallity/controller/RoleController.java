package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.model.Role;
import gr.mgourlis.draftnationallity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Secured("ADMIN")
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView showRoles(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roles = roleService.findAll();
        modelAndView.addObject("roles", roles);
        modelAndView.setViewName("showRoles");
        return modelAndView;
    }

}
