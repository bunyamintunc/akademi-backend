package com.bunyaminemre.paylasim.repository;


import com.bunyaminemre.paylasim.entitiy.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>{
    @Transactional
    List<Post> findByTickets_id(Long ticketId);
}
