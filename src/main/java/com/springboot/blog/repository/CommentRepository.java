package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query(value = "SELECT id, name, email, body, post_id, created_date FROM comments WHERE post_id = :postId", nativeQuery = true)
  List<Comment> findByPostId(long postId);


}
