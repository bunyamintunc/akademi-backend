package com.bunyaminemre.paylasim.controller;

import com.bunyaminemre.paylasim.entitiy.Role;
import com.bunyaminemre.paylasim.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public Role createRole(@RequestBody Role role){
        return roleService.saveRole(role);
    }

    @GetMapping("/getall")
    public List<Role> getRoles(){
        return roleService.getListRole();
    }

    @GetMapping("/getbyname/{name}")
    public Role getRoleByName(@PathVariable("name") String name){
        return roleService.findRoleByName(name);
    }
}
