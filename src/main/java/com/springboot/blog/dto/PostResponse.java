package com.springboot.blog.dto;

import java.util.List;

import com.springboot.blog.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean lastPage;
  private List<Post> content;
}
