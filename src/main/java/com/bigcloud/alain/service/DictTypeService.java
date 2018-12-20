package com.bigcloud.alain.service;

import com.bigcloud.alain.domain.Dict;
import com.bigcloud.alain.domain.DictType;
import com.bigcloud.alain.repository.DictRepository;
import com.bigcloud.alain.repository.DictTypeRepository;
import com.bigcloud.alain.service.dto.DictTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DictTypeService {

    private final Logger log = LoggerFactory.getLogger(DictTypeService.class);

    private final DictTypeRepository dictTypeRepository;

    private final DictRepository dictRepository;

    public DictTypeService(DictTypeRepository dictTypeRepository, DictRepository dictRepository) {
        this.dictTypeRepository = dictTypeRepository;
        this.dictRepository = dictRepository;
    }

    public boolean hasOneByCode(DictTypeDTO dictTypeDTO) {
        List<DictType> dictTypeList = dictTypeRepository.findOneByDictPid(dictTypeDTO.getDictPid());
        return dictTypeList.stream().filter(
            a -> dictTypeDTO.getValue().equals(a.getValue())
        ).findAny().isPresent();
    }

    public boolean findOneByCode(DictTypeDTO dictTypeDTO) {
        List<DictType> dictTypeList = dictTypeRepository.findOneByCode(dictTypeDTO.getValue(), dictTypeDTO.getDictPid());
        return dictTypeList.stream().filter(
            a -> !dictTypeDTO.getId().equals(a.getId().toString()) && dictTypeDTO.getValue().equals(a.getValue())
        ).findAny().isPresent();
    }

    public DictType createrDict(DictTypeDTO dictTypeDTO) {
        DictType dictType = new DictType();
        dictType.setId(dictTypeDTO.getId() != null ? Long.valueOf(dictTypeDTO.getId()) : null);
        dictType.setLabel(dictTypeDTO.getLabel() != null ? dictTypeDTO.getLabel() : null);
        dictType.setValue(dictTypeDTO.getValue() != null ? dictTypeDTO.getValue() : null);
        dictType.setSort(dictTypeDTO.getSort() != null ? dictTypeDTO.getSort() : null);
        if (dictTypeDTO.getDictPid() != null) {
            findDictById(Long.valueOf(dictTypeDTO.getDictPid())).ifPresent((value) -> {
                dictType.setDictParent(value);
            });
        }
        return dictType;
    }

    public Optional<Dict> findDictById(Long id) {
        return dictRepository.findDictById(id);
    }
}

