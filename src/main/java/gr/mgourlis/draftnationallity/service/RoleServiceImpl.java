package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Role;
import gr.mgourlis.draftnationallity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role findByName(String rolename) {
        String roleQuery = "ROLE_" + rolename.toUpperCase();
        return roleRepository.findByRole(roleQuery);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
}
