package gr.mgourlis.draftnationallity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.mgourlis.draftnationallity.model.Role;

@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);

}
