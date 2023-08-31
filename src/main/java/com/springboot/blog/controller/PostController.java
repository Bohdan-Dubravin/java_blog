package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

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
  public ResponseEntity<PostResponse> getPostsPagination(
      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_FIELD, required = false) String sortBy,
      @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortOrder) {
    return new ResponseEntity<>(postService.getPostPagination(size, page, sortBy, sortOrder), HttpStatus.OK);
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
