package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;

public interface PostService {
  PostDto createPost(PostDto postDto);

  List<Post> getAllPosts();

  Post getPostWithId(long postId);

  String updatePostById(PostDto post);

  String deletePostById(long postId);

  List<Post> getPostPagination(int size, int startFrom, String sortBy);
}
