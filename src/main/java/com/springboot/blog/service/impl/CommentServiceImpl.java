package com.springboot.blog.service.impl;

import java.util.ArrayList;
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

  public Comment mapToEntity(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setId(commentDto.getId());
    comment.setName(commentDto.getName());
    comment.setEmail(commentDto.getEmail());
    comment.setBody(commentDto.getBody());
    comment.setCreatedDate(commentDto.getCreatedDate());
    return comment;
  }

  @Override
  public List<CommentDto> findCommentByPostId(long postId) {

    List<Comment> comments = commentRepository.findByPostId(postId);

    if (comments.isEmpty()) {
      return new ArrayList<>();
    }

    List<CommentDto> commentsList = comments.stream().map((comment) -> mapToDto(comment)).collect(Collectors.toList());

    return commentsList;
  }

  @Override
  public CommentDto updateCommentById(long commentId, CommentDto commentRequest) {
    Comment oldComment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
    oldComment.setName(commentRequest.getName());
    oldComment.setBody(commentRequest.getBody());
    commentRequest.setEmail(commentRequest.getEmail());

    commentRepository.save(oldComment);

    return mapToDto(oldComment);
  }

  @Override
  public String deleteCommentById(long commentId) {
    commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

    commentRepository.deleteById(commentId);
    return "Comment with id: " + commentId + " deleted";
  }

}
