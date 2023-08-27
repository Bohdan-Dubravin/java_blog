package com.springboot.blog.controller;


import com.springboot.blog.bean.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class HelloWorldController {

  @GetMapping("/hello")
  public String helloWorld() {
    return "Hello World";
  }

  @GetMapping("/students")
  public ResponseEntity<Student[]> getStudent(@RequestParam("count") int count) {
    var student1 = new Student("23", "firstname", "class");
    var student2 = new Student("25", "firstnamesad ", "class sad das");
    var student3 = new Student("27", "firstnames da ", "class dsg er4g");
    var students = new Student[]{student1, student2, student3};
    return ResponseEntity.ok().header("customHeader", "values").body(Arrays.copyOfRange(students, 0, count < 1 ? 1 : count));
  }

  @GetMapping("/students/{id}")
  public Student getStudentById(@PathVariable("id") String id) {
    var student1 = new Student("23", "firstname", "class");
    var student2 = new Student("25", "firstnamesad ", "class sad das");
    var student3 = new Student("27", "firstnames da ", "class dsg er4g");

    var student = new ArrayList<>(Arrays.asList(student1, student2, student3)).stream().filter(st -> id.equals(st.getId())).findAny().orElse(null);

    if (student != null) {
      return student;
    } else {
      return null;
    }


  }
}
