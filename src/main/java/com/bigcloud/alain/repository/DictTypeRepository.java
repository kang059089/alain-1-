package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.DictType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the DictType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictTypeRepository extends JpaRepository<DictType, Long> {

    @Query(value = "SELECT * FROM bs_dict_type where dict_pid = ?1", nativeQuery = true)
    List<DictType> findOneByDictPid(String pid);

    @Query(value = "SELECT * FROM bs_dict_type where dict_pid = ?2 and code = ?1", nativeQuery = true)
    List<DictType> findOneByCode(String code, String pid);

    @Query(value = "SELECT * FROM bs_dict_type where id = ?1", nativeQuery = true)
    Optional<DictType> findDictTypeById(Long id);
}
