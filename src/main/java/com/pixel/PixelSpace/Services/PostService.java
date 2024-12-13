package com.pixel.PixelSpace.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Repositories.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void postCreate(Post post) {
        postRepository.save(post);
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("The post of id " + postId + " is not found"));
    }

    public void postDelete(Integer postId) {
        if (postRepository.findById(postId).isPresent()) {
            postRepository.deleteById(postId);
        }
    }

    public void postUpdate(Integer id, String title, String content) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The post of id " + id + " is not found"));
        postToUpdate.setTitle(title);
        postToUpdate.setContent(content);
        postRepository.save(postToUpdate);
    }

}
