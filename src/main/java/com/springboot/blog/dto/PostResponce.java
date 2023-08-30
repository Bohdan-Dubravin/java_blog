package com.springboot.blog.dto;

import java.util.List;

import com.springboot.blog.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponce {
  private List<Post> content;
  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean lastPage;
}
