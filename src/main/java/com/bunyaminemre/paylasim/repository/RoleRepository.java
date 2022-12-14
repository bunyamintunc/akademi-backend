package com.bunyaminemre.paylasim.repository;

import com.bunyaminemre.paylasim.entitiy.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
