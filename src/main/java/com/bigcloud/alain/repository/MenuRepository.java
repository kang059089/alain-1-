package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.Menu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "SELECT * FROM bs_menu where pid is null", nativeQuery = true)
    List<Menu> findMenu();

}
