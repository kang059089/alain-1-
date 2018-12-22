package com.bigcloud.alain.service;

import com.bigcloud.alain.domain.Role;
import com.bigcloud.alain.repository.RoleRepository;
import com.bigcloud.alain.service.dto.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createrRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId() != null ? Long.valueOf(roleDTO.getId()) : null);
        role.setAcl(roleDTO.getAcl() != null ? roleDTO.getAcl() : null);
        role.setName(roleDTO.getName() != null ? roleDTO.getName() : null);
        if (roleDTO.getPid() != null) {
            roleRepository.findRoleById(Long.valueOf(roleDTO.getPid())).ifPresent((value) -> {
                role.setParent(value);
            });
        }
        return role;
    }
}
