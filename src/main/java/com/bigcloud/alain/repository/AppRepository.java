package com.bigcloud.alain.repository;

import com.bigcloud.alain.domain.App;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the App entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppRepository extends JpaRepository<App, Long> {

    @Query(value = "SELECT * FROM bs_app where id = 1 ", nativeQuery = true)
    App getApp();
}
