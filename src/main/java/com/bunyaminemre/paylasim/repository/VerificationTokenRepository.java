package com.bunyaminemre.paylasim.repository;

import com.bunyaminemre.paylasim.entitiy.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {


    VerificationToken findByToken(String token);
}
