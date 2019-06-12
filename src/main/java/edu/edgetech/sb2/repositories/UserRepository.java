package edu.edgetech.sb2.repositories;

import edu.edgetech.sb2.models.User;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {         //	JpaRepository<User, Integer> {

}
