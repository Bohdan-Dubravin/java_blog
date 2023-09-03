package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.service.CommentService;

@RestController
public class CommentController {

  private CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId,
                                                  @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  @GetMapping("/posts/{postId}/comments")
  public ResponseEntity<List<CommentDto>> getPostWithComments(@PathVariable("postId") long postId) {
    return new ResponseEntity<>(commentService.findCommentByPostId(postId), HttpStatus.OK);
  }

  @PutMapping("/comments/{commentId}")
  public ResponseEntity<CommentDto> updateComment(@PathVariable("commentId") long commentId,
                                                  @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.updateCommentById(commentId, commentDto), HttpStatus.OK);
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable("commentId") long commentId) {
    return new ResponseEntity<>(commentService.deleteCommentById(commentId), HttpStatus.OK);
  }
}
