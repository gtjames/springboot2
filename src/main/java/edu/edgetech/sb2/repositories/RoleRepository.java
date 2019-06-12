package edu.edgetech.sb2.repositories;

import edu.edgetech.sb2.models.Role;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {			//	JpaRepository<Role, Integer> {

	public Role findByRole(String role);
}
