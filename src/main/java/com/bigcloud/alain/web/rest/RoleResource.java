package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.service.RoleService;
import com.bigcloud.alain.service.dto.RoleDTO;
import com.bigcloud.alain.service.dto.RoleTreeDTO;
import com.codahale.metrics.annotation.Timed;
import com.bigcloud.alain.domain.Role;
import com.bigcloud.alain.repository.RoleRepository;
import com.bigcloud.alain.web.rest.errors.BadRequestAlertException;
import com.bigcloud.alain.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Role.
 */
@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = "role";

    private final RoleRepository roleRepository;

    private final RoleService roleService;

    public RoleResource(RoleRepository roleRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    /**
     * POST  /roles : Create a new role.
     *
     * @param roleDTO the role to create
     * @return the ResponseEntity with status 201 (Created) and with body the new role, or with status 400 (Bad Request) if the role has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/roles")
    @Timed
    public ResponseEntity<Role> createRole(@RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("创建角色传入的参数 : {}", roleDTO);
        if (roleDTO.getId() != null) {
            throw new BadRequestAlertException("角色已存在", ENTITY_NAME, "idexists");
        }
        Role role = roleService.createrRole(roleDTO);
        Role result = roleRepository.save(role);
        return ResponseEntity.created(new URI("/api/roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /roles : Updates an existing role.
     *
     * @param roleDTO the role to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated role,
     * or with status 400 (Bad Request) if the role is not valid,
     * or with status 500 (Internal Server Error) if the role couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/roles")
    @Timed
    public ResponseEntity<Role> updateRole(@RequestBody RoleDTO roleDTO) throws URISyntaxException {
        log.debug("编辑角色传入的参数 : {}", roleDTO);
        if (roleDTO.getId() == null) {
            throw new BadRequestAlertException("角色不存在", ENTITY_NAME, "idnull");
        }
        Role role = roleService.createrRole(roleDTO);
        Role result = roleRepository.save(role);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, role.getId().toString()))
            .body(result);
    }

    /**
     * GET  /roles : get all the roles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of roles in body
     */
    @GetMapping("/roles")
    @Timed
    public List<Role> getAllRoles() {
        log.debug("REST request to get all Roles");
        return roleRepository.findAll();
    }

    /**
     * GET  /roles/:id : get the "id" role.
     *
     * @param id the id of the role to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the role, or with status 404 (Not Found)
     */
    @GetMapping("/roles/{id}")
    @Timed
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        log.debug("REST request to get Role : {}", id);
        Optional<Role> role = roleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(role);
    }

    /**
     * DELETE  /roles/:id : delete the "id" role.
     *
     * @param id the id of the role to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/roles/{id}")
    @Timed
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);

        roleRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * 获取所有角色树结构信息
     * @return 返回所有角色树结构信息
     */
    @GetMapping("/roleTree")
    @Timed
    public List<RoleTreeDTO> findRoleTree() {
        log.debug("获取所有角色树结构信息");
        List<RoleTreeDTO> roleTree = roleRepository.findRole().stream().map(RoleTreeDTO::new).collect(Collectors.toList());
        return roleTree;
    }
}
