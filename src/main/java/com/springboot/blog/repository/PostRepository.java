package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

  @Query(value = "SELECT * FROM posts", nativeQuery = true)
  public List<Post> getAllPosts();

  @Query(value = "SELECT * FROM posts WHERE id = :postId", nativeQuery = true)
  public Post getPostWithID(long postId);

  @Query("SELECT p FROM Post p  WHERE p.id = :postId")
  Post findPostWithComments(@Param("postId") Long postId);

  @Query(value = "SELECT * FROM posts ORDER BY :sortBy", nativeQuery = true)
  List<Post> getPostsPagination(
          String sortBy);

  @Modifying
  @Query(value = "update Posts p set p.title = :title, p.description = :description, p.content = :content where p.id = :postId", nativeQuery = true)
  public void updatePostById(@Param("postId") long postId, @Param("title") String title,
                             @Param("description") String description, @Param("content") String content);

  @Modifying
  @Query(value = "delete from Posts p where p.id = :postId", nativeQuery = true)
  public void deletePostWithId(@Param("postId") long postId);
}
