package com.pixel.PixelSpace.Services;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Exceptions.InvalidOperationException;
import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;

    public UserService(UserRepository userRepository, PostService postService, FriendshipService friendshipService,
            CommentService commentService, LikeService likeService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.friendshipService = friendshipService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    // --Profile Management----

    public void userRegister(User user) {
        userRepository.save(user);
    }

    public void userDelete(Integer id) {
        Optional<User> validUser = userRepository.findById(id);
        if (validUser.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        Optional<User> validUser = userRepository.findById(userId);
        return validUser
                .orElseThrow(() -> new ResourceNotFoundException("The user of id " + userId + " is not found"));
    }

    public User getUserByUsername(String username) {
        Optional<User> validUser = userRepository.findByUsername(username);
        return validUser
                .orElseThrow(() -> new ResourceNotFoundException(username + " is not found"));
    }

    public User userLogin(String username, String password) throws AuthenticationException {
        Optional<User> validLogin = userRepository.findByUsernameAndPassword(username, password);
        return validLogin.orElseThrow(() -> new AuthenticationException("The username or the password is incorrect"));
    }

    public void userUpdate(Integer id, String name, String bio, String email, String profileImg) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user of id " + id + " is not found"));
        userToUpdate.setName(name);
        userToUpdate.setBio(bio);
        userToUpdate.setEmail(email);
        userToUpdate.setProfileImg(profileImg);
        userRepository.save(userToUpdate);
    }

    // Post Actions
    @Transactional
    public void userMakePost(Integer userId, Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("The user of id " + userId + " is not found"));
        post.setUser(user);
        post.setTimeCreated(System.currentTimeMillis());
        postService.postCreate(post);
    }

    public List<Post> getUserPosts(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + userId + " not found"));
        return postService.getPostByUser(user);
    }

    @Transactional
    public void likePost(Integer userId, Integer postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + userId + " not found"));
        Post post = postService.getPostById(postId);
        Optional<Like> like = likeService.findLikeByUserAndPost(user, post);
        if (like.isPresent()) {
            throw new InvalidOperationException("You already liked the post");
        }
        Like newLike = new Like(post, user);
        likeService.createLike(newLike);
    }

    @Transactional
    public void unlikePost(Integer userId, Integer postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + userId + " not found"));
        Post post = postService.getPostById(postId);
        Like like = likeService.findLikeByUserAndPost(user, post)
                .orElseThrow(() -> new ResourceNotFoundException("User did not like this post yet"));
        likeService.deleteLike(like);
    }

    // ----Comment Actions----
    @Transactional
    public void userMakeComment(Integer userId, Integer postId, Comment comment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("The user of id " + userId + " is not found"));
        Post post = postService.getPostById(postId);
        comment.setUser(user);
        comment.setPost(post);
        comment.setTimeCreated(System.currentTimeMillis());
        commentService.createComment(comment);
    }

    @Transactional
    public void likePostComment(Integer userId, Integer postId, Integer commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + userId + " not found"));
        Post post = postService.getPostById(postId);
        Comment comment = commentService.getCommentById(commentId);
        Optional<Like> like = likeService.findLikeByUserAndPostAndComment(user, post, comment);
        if (like.isPresent()) {
            throw new InvalidOperationException("This user already liked this comment of this post");
        }
        Like newLike = new Like(post, comment, user);
        likeService.createLike(newLike);
    }

    @Transactional
    public void unlikePostComment(Integer userId, Integer postId, Integer commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + userId + " not found"));
        Post post = postService.getPostById(postId);
        Comment comment = commentService.getCommentById(commentId);
        Like like = likeService.findLikeByUserAndPostAndComment(user, post, comment)
                .orElseThrow(() -> new ResourceNotFoundException("User did not like this post\'s comment yet"));
        ;
        likeService.deleteLike(like);
    }

    // -----Friendship Actions-----
    @Transactional
    public void createFriendship(Integer userId1, Integer userId2) throws InvalidOperationException {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new ResourceNotFoundException("User1 not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new ResourceNotFoundException("User2 not found"));

        Optional<Friendship> optionalFriendship = friendshipService.findFriendshipByBothUser(user1, user2);
        if (optionalFriendship.isPresent()) {
            throw new InvalidOperationException("You are already friends");
        }

        if (!user1.equals(user2)) {
            Friendship friendship = new Friendship(user1, user2);
            friendshipService.createFriendship(friendship);
            user1.addInitiatedFriendship(friendship);
            user2.addReceivedFriendship(friendship);
        }
    }

    @Transactional
    public void deleteFriendship(Integer userId1, Integer userId2) throws InvalidOperationException {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new ResourceNotFoundException("User1 not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new ResourceNotFoundException("User2 not found"));

        Optional<Friendship> optionalFriendship = friendshipService.findFriendshipByBothUser(user1, user2);
        if (optionalFriendship.isPresent()) {
            Friendship friendship = optionalFriendship.get();
            friendshipService.deleteFriendship(friendship);
            user1.removeInitiatedFriendship(friendship);
            user2.removeReceivedFriendship(friendship);
        } else {
            throw new InvalidOperationException("You are not following this user yet.");
        }
    }

    public List<Friendship> getUser1Friendship(Integer userId1) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new ResourceNotFoundException("User1 not found"));

        return friendshipService.getFriendshipOfUser1(user1);
    }

}
