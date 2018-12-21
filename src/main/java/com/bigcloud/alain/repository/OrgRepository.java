package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.Menu;
import com.bigcloud.alain.domain.Org;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Org entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgRepository extends JpaRepository<Org, Long> {

    @Query(value = "SELECT * FROM bs_org where pid is null", nativeQuery = true)
    List<Org> findOrg();

    @Query(value = "SELECT * FROM bs_org order by jhi_sort", nativeQuery = true)
    Page<Org> findOrgPage(Pageable pageable);

    @Query(value = "SELECT * FROM bs_org where id = ?1", nativeQuery = true)
    Optional<Org> findOrgById(Long id);

    @Query(value = "SELECT * FROM bs_org where id = ?1 or code = ?1 or name = ?1", nativeQuery = true)
    Page<Org> getOrgByItem(String item, Pageable pageable);
}
