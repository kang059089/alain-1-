package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.DictType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DictType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictTypeRepository extends JpaRepository<DictType, Long> {

}
