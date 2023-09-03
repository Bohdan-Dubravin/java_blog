package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {


  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentServiceImpl commentService;

  @Override
  public PostDto createPost(PostDto postDto) {
    Post post = new Post();
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setDescription(postDto.getDescription());

    Post newPost = postRepository.save(post);
    PostDto postResponse = new PostDto();
    postResponse.setId(newPost.getId());
    postResponse.setTitle(newPost.getTitle());
    postResponse.setContent(newPost.getContent());
    postResponse.setDescription(newPost.getDescription());

    return postResponse;
  }

  @Override
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  @Override
  public Post getPostWithId(long postId) {
    Post foundPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
    if (foundPost == null) {
      throw new ResourceNotFoundException("Post", "id", String.valueOf(postId));
    }

//    Set<Comment> postComments = commentService.findCommentByPostId(postId).stream().map((com) -> commentService.mapToEntity(com)).collect(Collectors.toSet());
//
//    if (postComments != null) {
//      foundPost.setComments(postComments);
//    }
    return foundPost;
  }

  @Override
  public String updatePostById(PostDto post) {
    Post oldPost = getPostWithId(post.getId());

    if (oldPost == null) {
      throw new ResourceNotFoundException("Post", "id", String.valueOf(post.getId()));
    }
    postRepository.updatePostById(post.getId(), post.getTitle(), post.getDescription(), post.getContent());

    return "Post with id '" + post.getId() + "' updated";
  }

  @Override
  public String deletePostById(long postId) {
    Post oldPost = getPostWithId(postId);

    if (oldPost == null) {
      throw new ResourceNotFoundException("Post", "id", String.valueOf(postId));
    }

    postRepository.deletePostWithId(postId);

    return "Post with id '" + postId + "' deleted";
  }

  @Override
  public PostResponse getPostPagination(int size, int startFrom, String sortBy, String sortOrder) {
    int pageSize = size > 0 ? size : 1;
    Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(startFrom, pageSize, sort);
    Page<Post> posts = postRepository.findAll(pageable);
    PostResponse postResponse = new PostResponse();
    postResponse.setContent(posts.getContent());
    postResponse.setPageNumber(posts.getSize());
    postResponse.setPageSize(posts.getSize());
    postResponse.setTotalElements(posts.getTotalElements());
    postResponse.setTotalPages(posts.getTotalPages());
    postResponse.setLastPage(posts.isLast());

    return postResponse;
  }

}
