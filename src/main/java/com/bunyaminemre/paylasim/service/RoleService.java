package com.bunyaminemre.paylasim.service;

import com.bunyaminemre.paylasim.entitiy.Role;
import com.bunyaminemre.paylasim.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public List<Role> getListRole(){
        return roleRepository.findAll();
    }

    public void deleteRole(Long roleId){
        roleRepository.deleteById(roleId);
    }

    public Role findRoleByName(String name){
        return roleRepository.findByName(name);
    }
}

