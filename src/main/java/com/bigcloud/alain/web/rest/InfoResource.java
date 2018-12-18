package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.domain.App;
import com.bigcloud.alain.domain.Info;
import com.bigcloud.alain.repository.AppRepository;
import com.bigcloud.alain.repository.MenuRepository;
import com.bigcloud.alain.service.dto.MenuDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class InfoResource {

    private final Logger log = LoggerFactory.getLogger(InfoResource.class);

    private AppRepository appRepository;

    private MenuRepository menuRepository;

    public InfoResource(AppRepository appRepository, MenuRepository menuRepository) {
        this.appRepository = appRepository;
        this.menuRepository = menuRepository;
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
        return ResponseEntity.ok().body(info);
    }
}
