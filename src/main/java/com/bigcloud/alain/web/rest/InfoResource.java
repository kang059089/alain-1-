package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.domain.*;
import com.bigcloud.alain.repository.AppRepository;
import com.bigcloud.alain.repository.MenuRepository;
import com.bigcloud.alain.repository.UserRepository;
import com.bigcloud.alain.security.SecurityUtils;
import com.bigcloud.alain.service.dto.MenuDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class InfoResource {

    private final Logger log = LoggerFactory.getLogger(InfoResource.class);

    private AppRepository appRepository;

    private MenuRepository menuRepository;

    private UserRepository userRepository;

    public InfoResource(AppRepository appRepository, MenuRepository menuRepository, UserRepository userRepository) {
        this.appRepository = appRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/info")
    @Timed
    public ResponseEntity<Info> getAppInfo() {
        log.debug("登录的时候获取应用信息、菜单信息");
        Info info = new Info();
        // 获取应用信息（包括站点名、描述、年份）
        App app = appRepository.getApp();
        // 获取左侧菜单树结构信息
        List<MenuDTO> menuList = menuRepository.findMenu().stream().sorted().map(MenuDTO::new).collect(Collectors.toList());
        info.setApp( app != null ? app : new App() );
        info.setMenuList( menuList.size() > 0 ? menuList : new ArrayList<MenuDTO>());
        // 获取登录用户权限acls
        Set<String> acls = new HashSet<>();
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        String login = currentUserLogin.get();
        if (login != null || "".equals(login)) {
            User user = this.userRepository.findByLogin(login);
            if (!user.getRoles().isEmpty()) {
                for ( Role role : user.getRoles() ) {
                    String acl = role.getAcl();
                    String[] strs = acl.split(",");
                    for ( String str : strs ) {
                        acls.add(str);
                    }
                }
            }
        }
        info.setAcls(acls);
        return ResponseEntity.ok().body(info);
    }
}
