package com.validation.student.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.validation.student.model.Student;

public interface StudentRepo extends MongoRepository <Student,String>{
    List<Student> findByTitleContaining(String title);
    List<Student> findByPublished(boolean published);
    
}
