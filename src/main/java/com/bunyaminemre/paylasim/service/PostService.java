package com.bunyaminemre.paylasim.service;

import com.bunyaminemre.paylasim.dto.PostDto;
import com.bunyaminemre.paylasim.entitiy.Post;
import com.bunyaminemre.paylasim.entitiy.Ticket;
import com.bunyaminemre.paylasim.repository.PostRepository;
import com.bunyaminemre.paylasim.repository.TicketRepository;
import com.bunyaminemre.paylasim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.bunyaminemre.paylasim.entitiy.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired 
    private PostRepository postRepository;

    public void savePost(PostDto postDto) throws IOException {
        Post post = null;
        post = createFileOfPost(postDto);

        postRepository.save(post);
    }

    public Post createFileOfPost(PostDto postDto) throws IOException {
        MultipartFile fileOfPost = postDto.getUploadFile();

        User user = userRepository.findById(postDto.getUserId()).orElse(null);
        Ticket ticket = ticketRepository.findById(postDto.getTicketId()).orElse(null);

        String fileName = StringUtils.cleanPath(fileOfPost.getName());
        Post newPost = Post.builder().tickets(new HashSet<>()).name(fileName).description(postDto.getDescription()).fileType(fileOfPost.getContentType()).data(fileOfPost.getBytes()).user(user).build();
        newPost.getTickets().add(ticket);
        newPost.setPostName(postDto.getPostName());

        return newPost;
    }

    public ResponseEntity<Resource> downloadPostWithId(Long postId) throws Exception {

        Post post = postRepository.findById(postId).orElseThrow(()-> new Exception("post does not found"));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(post.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"post; filename\""+ post.getName()+"\"")
                .body(new ByteArrayResource(post.getData()));
    }


    public List<Post> getPostsByTicket(Long ticketId){
        return postRepository.findByTickets_id(ticketId);
    }

    public List<Post> getPostList() {
        return postRepository.findAll();
    }
}
