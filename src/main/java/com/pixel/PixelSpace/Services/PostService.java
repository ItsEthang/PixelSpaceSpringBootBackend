package com.pixel.PixelSpace.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;

    public PostService(PostRepository postRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
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

    public User getPostUser(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post" + postId + " not found"));
        return post.getUser();
    }

    public List<Comment> getPostComment(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post" + postId + " not found"));
        return commentService.getAllCommentByPost(post);
    }

    public List<Post> getPostByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    public void postDelete(Integer postId) {
        if (postRepository.findById(postId).isPresent()) {
            postRepository.deleteById(postId);
        } else {
            throw new ResourceNotFoundException("Post " + postId + " not found");
        }
    }

    public void postUpdate(Integer id, String title, String content) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The post of id " + id + " is not found"));
        postToUpdate.setTitle(title);
        postToUpdate.setContent(content);
        postRepository.save(postToUpdate);
    }

    public Integer getLikeCount(Integer id) {
        Post targetPost = this.getPostById(id);
        List<Like> allLikes = targetPost.getLikes();
        Integer res = 0;

        for (Like like : allLikes) {
            if (like.getComment() == null) {
                res += 1;
            }
        }

        return res;
    }

}
