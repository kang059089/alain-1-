package com.bigcloud.alain.service;

import com.bigcloud.alain.domain.Button;
import com.bigcloud.alain.domain.Menu;
import com.bigcloud.alain.repository.ButtonRepository;
import com.bigcloud.alain.repository.MenuRepository;
import com.bigcloud.alain.service.dto.ButtonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ButtonService {

    private final Logger log = LoggerFactory.getLogger(ButtonService.class);

    private final ButtonRepository buttonRepository;

    private final MenuRepository menuRepository;

    public ButtonService(ButtonRepository buttonRepository, MenuRepository menuRepository) {
        this.buttonRepository = buttonRepository;
        this.menuRepository = menuRepository;
    }

    public Button createrButton(ButtonDTO buttonDTO) {
        Button button = new Button();
        button.setId(buttonDTO.getId() != null ? Long.valueOf(buttonDTO.getId()) : null);
        button.setName(buttonDTO.getName() != null ? buttonDTO.getName() : null);
        button.setAcl(buttonDTO.getAcl() != null ? buttonDTO.getAcl() : null);
        button.setSort(buttonDTO.getSort() != null ? buttonDTO.getSort() : null);
        button.setDescription(buttonDTO.getDescription() != null ? buttonDTO.getDescription() : null);
        if (buttonDTO.getMenuPid() != null) {
            menuRepository.findMenuById(Long.valueOf(buttonDTO.getMenuPid())).ifPresent((value) -> {
                button.setMenuParent(value);
            });
        }
        return button;
    }
}
