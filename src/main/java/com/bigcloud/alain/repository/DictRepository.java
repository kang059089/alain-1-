package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.Dict;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictRepository extends JpaRepository<Dict, Long> {

}
