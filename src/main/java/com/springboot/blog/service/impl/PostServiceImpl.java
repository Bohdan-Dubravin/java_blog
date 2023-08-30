package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private PostRepository postRepository;

  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

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
    return postRepository.getAllPosts();
  }

  @Override
  public Post getPostWithId(long postId) {
    Post foundPost = postRepository.getPostWithID(postId);

    if (foundPost == null) {
      throw new ResourceNotFoundException("Post", "id", String.valueOf(postId));
    }

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
  public List<Post> getPostPagination(int size, int startFrom, String sortBy) {
    int startIndex = (int) Math.ceil(startFrom);
    int pageSize = size > 0 ? size : 1;
    String sortingColumn = sortBy.length() > 0 ? sortBy : "title";
    Pageable pageable = PageRequest.of(startIndex, pageSize);
    List<Post> posts = postRepository.findAll(pageable).getContent();

    return posts;
  }

}
