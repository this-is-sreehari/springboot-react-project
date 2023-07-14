package com.validation.student.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.validation.student.model.Student;
import com.validation.student.repository.StudentRepo;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class StudentController {
    
    @Autowired
    StudentRepo studentRepo;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String title) {
        try {
          List<Student> students = new ArrayList<Student>();
      
          if (title == null)
          studentRepo.findAll().forEach(students::add);
          else
          studentRepo.findByTitleContaining(title).forEach(students::add);
      
          if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
      
          return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
  
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") String id) {
        Optional<Student> studentData = studentRepo.findById(id);
      
        if (studentData.isPresent()) {
          return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
      
  
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
          Student _student = studentRepo.save(new Student(student.getTitle(), student.getDescription(), false));
          return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
  
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") String id, @RequestBody Student student) {
        Optional<Student> studentData = studentRepo.findById(id);
      
        if (studentData.isPresent()) {
          Student _student = studentData.get();
          _student.setTitle(student.getTitle());
          _student.setDescription(student.getDescription());
          _student.setPublished(student.isPublished());
          return new ResponseEntity<>(studentRepo.save(_student), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }
  
    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") String id) {
        try {
            studentRepo.deleteById(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
  
    @DeleteMapping("/students")
    public ResponseEntity<HttpStatus> deleteAllStudents() {
        try {
          studentRepo.deleteAll();
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
  
    @GetMapping("/students/published")
    public ResponseEntity<List<Student>> findByPublished() {
        try {
          List<Student> students = studentRepo.findByPublished(true);
      
          if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

}
