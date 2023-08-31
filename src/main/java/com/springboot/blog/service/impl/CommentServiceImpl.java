package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private PostRepository postRepository;

  @Override
  public CommentDto createComment(long postId, CommentDto commentDto) {
    Comment comment = mapToEntity(commentDto);
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

    comment.setPost(post);

    Comment newComment = commentRepository.save(comment);

    return mapToDto(newComment);

  }

  private CommentDto mapToDto(Comment comment) {
    CommentDto commentDto = new CommentDto();
    commentDto.setId(comment.getId());
    commentDto.setName(comment.getName());
    commentDto.setEmail(comment.getEmail());
    commentDto.setBody(comment.getBody());
    commentDto.setCreatedDate(comment.getCreatedDate());
    return commentDto;
  }

  private Comment mapToEntity(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setId(commentDto.getId());
    comment.setName(commentDto.getName());
    comment.setEmail(commentDto.getEmail());
    comment.setBody(commentDto.getBody());
    comment.setCreatedDate(commentDto.getCreatedDate());
    return comment;
  }

  @Override
  public List<CommentDto> findByPostId(long postId) {

    List<Comment> comments = commentRepository.findByPostId(postId);

    if (comments.isEmpty()) {
      throw new ResourceNotFoundException("Comments", "post_id", String.valueOf(postId));
    }

    List<CommentDto> commentsList = comments.stream().map((comment) -> mapToDto(comment)).collect(Collectors.toList());

    return commentsList;
  }

}
