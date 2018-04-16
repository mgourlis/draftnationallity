package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Role;
import gr.mgourlis.draftnationallity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String rolename) {
        return roleRepository.findByRole(rolename);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
