package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.service.PostService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("posts")
public class PostController {

  @Autowired
  private PostService postService;

  @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

  }

  @GetMapping
  public ResponseEntity<List<Post>> getAllPosts() {
    return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable("id") long id) {
    return new ResponseEntity<>(postService.getPostWithId(id), HttpStatus.OK);
  }

  @GetMapping("/paginate")
  public ResponseEntity<List<Post>> getPostsPagination(
      @RequestParam(value = "size", defaultValue = "5", required = false) int size,
      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
      @RequestParam("sortBy") String sortBy) {
    return new ResponseEntity<>(postService.getPostPagination(size, page, sortBy), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updatePostById(@PathVariable("id") long id, @RequestBody() PostDto postDto) {
    postDto.setId(id);
    return new ResponseEntity<>(postService.updatePostById(postDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePostById(@PathVariable("id") long id) {
    return new ResponseEntity<>(postService.deletePostById(id), HttpStatus.OK);
  }
}
