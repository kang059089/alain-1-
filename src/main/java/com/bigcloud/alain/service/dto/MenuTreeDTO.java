package com.bigcloud.alain.service.dto;

import com.bigcloud.alain.domain.Menu;

import java.util.List;
import java.util.stream.Collectors;

public class MenuTreeDTO extends BaseTreeDTO {

    private List<ButtonDTO> btnChildren;

    public MenuTreeDTO() {
    }

    public MenuTreeDTO(Menu menu) {
        this.key = menu.getId() + "";
        if (null != menu.getParent()) this.pid = menu.getParent().getId() + "";
        this.title = menu.getName();
        this.acl = menu.getAcl();
        this.expanded = true; // 树结构默认展开
        if (!menu.getMenus().isEmpty()) {
            this.children = menu.getMenus().stream().sorted().map(MenuTreeDTO::new).collect(Collectors.toList());
        } else {
            this.isLeaf = true;
        }
        this.btnChildren = menu.getButtons().stream().map(ButtonDTO::new).collect(Collectors.toList());
    }

    public List<ButtonDTO> getBtnChildren() {
        return btnChildren;
    }

    public void setBtnChildren(List<ButtonDTO> btnChildren) {
        this.btnChildren = btnChildren;
    }
}
