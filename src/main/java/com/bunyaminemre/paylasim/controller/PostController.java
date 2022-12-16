package com.bunyaminemre.paylasim.controller;

import com.bunyaminemre.paylasim.dto.PostDto;
import com.bunyaminemre.paylasim.entitiy.Post;
import com.bunyaminemre.paylasim.repository.UserRepository;
import com.bunyaminemre.paylasim.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;
    private final UserRepository userRepository;

    @PostMapping("/upload")
    public void uploadPost(@ModelAttribute PostDto postDto) throws IOException {


        postService.savePost(postDto);
    }

    @GetMapping("/download/{postId}")
    public ResponseEntity<Resource> downloadFileOfPost(@PathVariable("postId") Long postId) throws Exception {
          return postService.downloadPostWithId(postId);
    }

    @GetMapping("/getbyticketid/{ticketId}")
    public List<Post> getByTicket(@PathVariable("ticketId") Long ticketId){
        return postService.getPostsByTicket(ticketId);
    }
}
