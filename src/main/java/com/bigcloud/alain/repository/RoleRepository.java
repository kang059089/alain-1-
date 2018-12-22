package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM bs_role where pid is null", nativeQuery = true)
    List<Role> findRole();

    @Query(value = "SELECT * FROM bs_role where id = ?1", nativeQuery = true)
    Optional<Role> findRoleById(Long id);

}
