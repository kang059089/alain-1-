package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.Dict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Dict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictRepository extends JpaRepository<Dict, Long> {

    @Query(value = "SELECT * FROM bs_dict where jhi_type = 'org_type'", nativeQuery = true)
    Dict getDictOrg();

    Optional<Dict> findOneByType(String type);

    @Query(value = "SELECT * FROM bs_dict where jhi_type = ?1 or name = ?1", nativeQuery = true)
    Page<Dict> getDictByItem(String item, Pageable pageable);

    @Query(value = "SELECT * FROM bs_dict where id = ?1", nativeQuery = true)
    Optional<Dict> findDictById(Long id);
}
