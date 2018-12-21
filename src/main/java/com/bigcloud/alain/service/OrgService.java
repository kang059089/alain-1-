package com.bigcloud.alain.service;

import com.bigcloud.alain.domain.DictType;
import com.bigcloud.alain.domain.Org;
import com.bigcloud.alain.repository.DictTypeRepository;
import com.bigcloud.alain.repository.OrgRepository;
import com.bigcloud.alain.service.dto.OrgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrgService {

    private final Logger log = LoggerFactory.getLogger(OrgService.class);

    private final OrgRepository orgRepository;

    private final DictTypeRepository dictTypeRepository;

    public OrgService(OrgRepository orgRepository, DictTypeRepository dictTypeRepository) {
        this.orgRepository = orgRepository;
        this.dictTypeRepository = dictTypeRepository;
    }

    public Page<OrgDTO> findOrgPage(Pageable pageable) {
        return orgRepository.findOrgPage(pageable).map(OrgDTO::new);
    }

    public Optional<DictType> findDictTypeById(Long id) {
        return dictTypeRepository.findDictTypeById(id);
    }

    public Optional<Org> findOrgById(Long id) {
        return orgRepository.findOrgById(id);
    }

    public Page<OrgDTO> getOrgByItemPage(String item, Pageable pageable) {
        return orgRepository.getOrgByItem(item, pageable).map(OrgDTO::new);
    }

    public boolean isOrgTree(OrgDTO orgDTO) {
        if (orgDTO.getChildren() == null) {
            return false;
        } else {
            for (OrgDTO orgDTO1 : orgDTO.getChildren()) {
                if (orgDTO.getPid().equals(orgDTO1.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Org createrOrg(OrgDTO orgDTO) {
        Org org = new Org();
        org.setId(orgDTO.getId() != null ? Long.valueOf(orgDTO.getId()) : null);
        org.setName(orgDTO.getTitle() != null ? orgDTO.getTitle() : null);
        org.setCode(orgDTO.getCode() != null ? orgDTO.getCode() : null);
        if (orgDTO.getDictOrgTypeId() != null) {
            findDictTypeById(Long.valueOf(orgDTO.getDictOrgTypeId())).ifPresent((value) -> {
                org.setType(value);
            });
        }
        org.setTelephone(orgDTO.getTelephone() != null ? orgDTO.getTelephone() : null);
        org.setFax(orgDTO.getFax() != null ? orgDTO.getFax() : null);
        org.setAddress(orgDTO.getAddress() != null ? orgDTO.getAddress() : null);
        org.setLongitude(orgDTO.getLongitude() != null ? orgDTO.getLongitude() : null);
        org.setLatitude(orgDTO.getLatitude() != null ? orgDTO.getLatitude() : null);
        org.setDescription(orgDTO.getDescription() != null ? orgDTO.getDescription() : null);
        org.setSort(orgDTO.getSort() != null ? orgDTO.getSort() : null);
        if (orgDTO.getPid() != null) {
            findOrgById(Long.valueOf(orgDTO.getPid())).ifPresent((value) -> {
                org.setParent(value);
            });
        }
        return org;
    }
}
