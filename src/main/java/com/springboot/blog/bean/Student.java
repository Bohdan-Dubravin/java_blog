package com.springboot.blog.bean;

import lombok.Data;

@Data
public class Student {

  private String id;
  private String name;
  private String classN;

  public Student(String id, String name, String classN) {
    this.id = id;
    this.name = name;
    this.classN = classN;
  }
}
