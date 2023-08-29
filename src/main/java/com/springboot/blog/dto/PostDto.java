package com.springboot.blog.dto;


import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class PostDto {
  long id;
  String title;
  String description;
  String content;
}
