package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "SELECT * FROM bs_menu where pid is null", nativeQuery = true)
    List<Menu> findMenu();

    @Query(value = "SELECT * FROM bs_menu where pid is null order by jhi_sort", nativeQuery = true)
    Page<Menu> findMenuPage(Pageable pageable);

    @Query(value = "SELECT * FROM bs_menu where name = ?1", nativeQuery = true)
    Page<Menu> findMenuByItemPage(String item, Pageable pageable);

    @Query(value = "SELECT * FROM bs_menu where id = ?1", nativeQuery = true)
    Optional<Menu> findMenuById(Long id);

}
