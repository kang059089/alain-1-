package com.bigcloud.alain.service;

import com.bigcloud.alain.domain.Dict;
import com.bigcloud.alain.repository.DictRepository;
import com.bigcloud.alain.service.dto.DictDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DictService {

    private final Logger log = LoggerFactory.getLogger(DictService.class);

    private final DictRepository dictRepository;

    public DictService(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    public Page<DictDTO> getDictsPage(Pageable pageable) {
        return dictRepository.findAll(pageable).map(DictDTO::new);
    }

    public boolean hasOneByType(String type) {
        return dictRepository.findOneByType(type).isPresent();
    }

    public Optional<Dict> findOneByType(String type) {
        return dictRepository.findOneByType(type);
    }

    public Page<DictDTO> getDictByItemPage(String item, Pageable pageable) {
        return dictRepository.getDictByItem(item, pageable).map(DictDTO::new);
    }

    public Dict createrDict(DictDTO dictDTO) {
        Dict dict = new Dict();
        dict.setId(dictDTO.getId() != null ? Long.valueOf(dictDTO.getId()) : null);
        dict.setName(dictDTO.getName() != null ? dictDTO.getName() : null);
        dict.setType(dictDTO.getType() != null ? dictDTO.getType() : null);
        return dict;
    }

}
