package com.bunyaminemre.paylasim.repository;

import com.bunyaminemre.paylasim.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findByEmail(String email);

    User findByUsername(String username);

    User findByUsernameAndEmail(String username, String email);
}
