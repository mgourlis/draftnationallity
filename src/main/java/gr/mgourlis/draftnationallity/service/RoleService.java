package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.Role;

import java.util.List;

public interface RoleService {
    public List<Role> findAll();
    public Role findById(Long id);
    public Role findByName(String rolename);
    public void save(Role role);
}
