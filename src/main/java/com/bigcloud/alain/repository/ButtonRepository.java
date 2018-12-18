package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.Button;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Button entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ButtonRepository extends JpaRepository<Button, Long> {

}
