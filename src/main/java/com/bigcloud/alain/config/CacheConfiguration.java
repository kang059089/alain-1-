package com.bigcloud.alain.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.bigcloud.alain.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.App.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Menu.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Menu.class.getName() + ".menus", jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Menu.class.getName() + ".buttons", jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Button.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Org.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Org.class.getName() + ".orgs", jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Dict.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Dict.class.getName() + ".dictTypes", jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.DictType.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Role.class.getName(), jcacheConfiguration);
            cm.createCache(com.bigcloud.alain.domain.Role.class.getName() + ".roles", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
