package gr.mgourlis.draftnationallity.configuration;

import gr.mgourlis.draftnationallity.model.Role;
import gr.mgourlis.draftnationallity.model.User;
import gr.mgourlis.draftnationallity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class Initializer {

    @Autowired
    private UserRepository userRepository;

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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @EventListener
    public void inititialize(ApplicationReadyEvent event){

        //-------------INIT ROLES---------------
        Role roleAdmin = new Role();
        roleAdmin.setRole("ADMIN");
        roleRepository.save(roleAdmin);
        Role roleModerator = new Role();
        roleModerator.setRole("MODERATOR");
        roleRepository.save(roleModerator);
        Role roleUser = new Role();
        roleUser.setRole("USER");
        roleRepository.save(roleUser);
        //--------------------------------------

        //-------------INIT USERS---------------
        Role role;

        User userAdmin = new User();
        userAdmin.setName("Myron");
        userAdmin.setLastName("Gourlis");
        userAdmin.setPassword(bCryptPasswordEncoder.encode("123456"));
        userAdmin.setEmail("admin@test.gr");
        userAdmin.setActive(1);
        role = roleRepository.findByRole("ADMIN");
        userAdmin.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userRepository.save(userAdmin);

        User userModerator = new User();
        userModerator.setName("MyronMod");
        userModerator.setLastName("GourlisMod");
        userModerator.setPassword(bCryptPasswordEncoder.encode("123456"));
        userModerator.setEmail("moderator@test.gr");
        userModerator.setActive(1);
        role = roleRepository.findByRole("MODERATOR");
        userModerator.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userRepository.save(userModerator);

        User userUser = new User();
        userUser.setName("MyronUser");
        userUser.setLastName("GourlisUser");
        userUser.setPassword(bCryptPasswordEncoder.encode("123456"));
        userUser.setEmail("user@test.gr");
        userUser.setActive(1);
        role = roleRepository.findByRole("USER");
        userUser.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userRepository.save(userUser);
        //--------------------------------------

    }
}
