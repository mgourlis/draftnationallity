package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    public List<Role> findAll();
    public Role findByName(String rolename);
    public void save(Role role);
}
