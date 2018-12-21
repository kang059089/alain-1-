package com.bigcloud.alain.service;

import com.bigcloud.alain.domain.Menu;
import com.bigcloud.alain.repository.MenuRepository;
import com.bigcloud.alain.service.dto.MenuDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MenuService {

    private final Logger log = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Page<MenuDTO> findMenuPage(Pageable pageable) {
        return menuRepository.findMenuPage(pageable).map(MenuDTO::new);
    }

    public Page<MenuDTO> findMenuByItemPage(String item, Pageable pageable) {
        return menuRepository.findMenuByItemPage(item, pageable).map(MenuDTO::new);
    }

    public Optional<Menu> findMenuById(Long id) {
        return menuRepository.findMenuById(id);
    }

    public boolean isMenuTree(MenuDTO menuDTO) {
        if (menuDTO.getChildren() == null) {
            return false;
        } else {
            for (MenuDTO menuDTO1 : menuDTO.getChildren()) {
                if (menuDTO.getPid().equals(menuDTO1.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Menu createrMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        menu.setId(menuDTO.getId() != null ? Long.valueOf(menuDTO.getId()) : null);
        menu.setName(menuDTO.getText() != null ? menuDTO.getText() : null);
        menu.seti18n(menuDTO.getI18n() != null ? menuDTO.getI18n() : null);
        menu.setGroup(menuDTO.getGroup() != null ? menuDTO.getGroup() : null);
        menu.setLink(menuDTO.getLink() != null ? menuDTO.getLink() : null);
        menu.setLinkExact(menuDTO.getLinkExact() != null ? menuDTO.getLinkExact() : null);
        menu.setTarget(menuDTO.getTarget() != null ? menuDTO.getTarget() : null);
        menu.setIcon(menuDTO.getIcon() != null ? menuDTO.getIcon().getValue() : null);
        menu.setAcl(menuDTO.getAcl() != null ? menuDTO.getAcl() : null);
        menu.setBadge(menuDTO.getBadge() != null ? menuDTO.getBadge() : null);
        menu.setBadgeDot(menuDTO.getBadgeDot() != null ? menuDTO.getBadgeDot() : null);
        menu.setBadgeStatus(menuDTO.getBadgeStatus() != null ? menuDTO.getBadgeStatus() : null);
        menu.setHide(menuDTO.getHide() != null ? menuDTO.getHide() : null);
        menu.setHideInBreadcrumb(menuDTO.getHideInBreadcrumb() != null ? menuDTO.getHideInBreadcrumb() : null);
        menu.setShortcut(menuDTO.getShortcut() != null ? menuDTO.getShortcut() : null);
        menu.setShortcuRoot(menuDTO.getShortcuRoot() != null ? menuDTO.getShortcuRoot() : null);
        menu.setReuse(menuDTO.getReuse() != null ? menuDTO.getReuse() : null);
        menu.setSort(menuDTO.getSort() != null ? menuDTO.getSort() : null);
        if (menuDTO.getPid() != null) {
            findMenuById(Long.valueOf(menuDTO.getPid())).ifPresent((value) -> {
                menu.setParent(value);
            });
        }
        return menu;
    }
}
