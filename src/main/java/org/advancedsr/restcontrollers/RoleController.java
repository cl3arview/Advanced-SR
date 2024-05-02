package org.advancedsr.restcontrollers;

import org.advancedsr.dtos.RoleDTO;
import org.advancedsr.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<RoleDTO> getRoleByName(@PathVariable String roleName) {
        RoleDTO role = roleService.getRoleByName(roleName);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO newRole = roleService.createRole(roleDTO);
        return ResponseEntity.ok(newRole);
    }

    @PutMapping("/{roleName}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable String roleName, @RequestBody RoleDTO roleDTO) {
        RoleDTO updatedRole = roleService.updateRole(roleName, roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity<Void> deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ResponseEntity.ok().build();
    }
}