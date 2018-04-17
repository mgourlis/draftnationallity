package gr.mgourlis.draftnationallity.configuration;

import gr.mgourlis.draftnationallity.model.Role;
import gr.mgourlis.draftnationallity.model.User;
import gr.mgourlis.draftnationallity.repository.*;
import gr.mgourlis.draftnationallity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class Initializer {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionCategoryRepository questionCategoryRepository;

    @Autowired
    private DifficultySettingRepository difficultySettingRepository;

    @Autowired
    private ExamSettingRepository examSettingRepository;

    @Autowired
    private ExamRepository examRepository;


    @EventListener
    public void inititialize(ApplicationReadyEvent event){

        //-------------INIT ROLES---------------
        Role roleAdmin = new Role();
        roleAdmin.setRole("ROLE_ADMIN");
        roleRepository.save(roleAdmin);
        Role roleModerator = new Role();
        roleModerator.setRole("ROLE_MODERATOR");
        roleRepository.save(roleModerator);
        Role roleUser = new Role();
        roleUser.setRole("ROLE_USER");
        roleRepository.save(roleUser);
        //--------------------------------------

        //-------------INIT USERS---------------
        Role role;

        User userAdmin = new User();
        userAdmin.setName("Myron");
        userAdmin.setLastName("Gourlis");
        userAdmin.setPassword("123456");
        userAdmin.setEmail("admin@test.gr");
        userAdmin.setActive(true);
        role = roleRepository.findByRole("ROLE_ADMIN");
        userAdmin.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userService.save(userAdmin);

        User userModerator = new User();
        userModerator.setName("MyronMod");
        userModerator.setLastName("GourlisMod");
        userModerator.setPassword("123456");
        userModerator.setEmail("moderator@test.gr");
        userModerator.setActive(true);
        role = roleRepository.findByRole("ROLE_MODERATOR");
        userModerator.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userService.save(userModerator);

        User userUser = new User();
        userUser.setName("MyronUser");
        userUser.setLastName("GourlisUser");
        userUser.setPassword("123456");
        userUser.setEmail("user@test.gr");
        userUser.setActive(true);
        role = roleRepository.findByRole("ROLE_USER");
        userUser.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userService.save(userUser);
        //--------------------------------------

    }
}
