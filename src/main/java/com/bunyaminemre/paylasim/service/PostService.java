package com.bunyaminemre.paylasim.service;

import com.bunyaminemre.paylasim.dto.PostDto;
import com.bunyaminemre.paylasim.entitiy.Post;
import com.bunyaminemre.paylasim.repository.PostRepository;
import com.bunyaminemre.paylasim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.bunyaminemre.paylasim.entitiy.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;
    
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
        String fileName = StringUtils.cleanPath(fileOfPost.getName());
        Post newPost = Post.builder().name(fileName).description(postDto.getDescription()).fileType(fileOfPost.getContentType()).data(fileOfPost.getBytes()).user(user).build();

        return newPost;
    }

}
