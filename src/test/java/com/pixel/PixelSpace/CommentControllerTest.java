package com.pixel.PixelSpace;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pixel.PixelSpace.Controllers.CommentController;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Models.ResponseEntities.UserResponse;
import com.pixel.PixelSpace.Services.CommentService;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @MockBean
    private Comment comment;

    @MockBean
    private User user;

    @Test
    void testGetAllComments() throws Exception {
        // Given: A list of comments
        List<Comment> comments = new ArrayList<>();
        when(commentService.getAllComment()).thenReturn(comments);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/comment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(comments.size()));

        verify(commentService, times(1)).getAllComment();
    }

    @Test
    void testDeleteComment() throws Exception {
        // Given: A comment ID
        Integer commentId = 1;

        // Perform the DELETE request and validate the response
        mockMvc.perform(delete("/comment/{id}", commentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment deleted"));

        // Verify that commentService.deleteComment was called once
        verify(commentService, times(1)).deleteComment(commentId);
    }

    @Test
    void testGetCommentUser() throws Exception {
        // Given: A comment ID and a user
        Integer commentId = 1;
        UserResponse userResponse = new UserResponse(1, "testUser", "Test User", "test.jpg");
        when(commentService.getCommentUser(commentId)).thenReturn(user);

        // Assuming `commentService.getCommentUser()` returns the user associated with
        // the comment
        when(user.getUserId()).thenReturn(1);
        when(user.getUsername()).thenReturn("testUser");
        when(user.getName()).thenReturn("Test User");
        when(user.getProfileImg()).thenReturn("test.jpg");

        // Perform the GET request and validate the response
        mockMvc.perform(get("/comment/{id}/user", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.profileImg").value("test.jpg"));
    }

    @Test
    void testGetCommentLikeCount() throws Exception {
        // Given: A comment ID and a like count
        Integer commentId = 1;
        Integer likeCount = 5;
        when(commentService.getLikeCount(commentId)).thenReturn(likeCount);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/comment/{id}/like", commentId))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
