package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;

public interface CommentService {

  CommentDto createComment(long postId, CommentDto commentDto);

  List<CommentDto> findByPostId(long postId);
}
