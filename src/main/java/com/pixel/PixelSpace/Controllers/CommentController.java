package com.pixel.PixelSpace.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Services.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> commentGetAll() {
        return ResponseEntity.status(200).body(commentService.getAllComment());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> postDelete(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().body("Comment deleted");
    }
}
