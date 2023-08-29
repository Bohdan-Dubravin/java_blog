package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
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
}
