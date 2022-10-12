package com.example.student.service;


import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Subject;
import com.example.student.reprository.StudentReprository;
import com.example.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentReprository studentReprository;
    private final SubjectService subjectService;

    @Autowired
    public StudentService(StudentReprository studentReprository, SubjectService subjectService) {
        this.studentReprository = studentReprository;
        this.subjectService = subjectService;
    }

    public List<Student> getStudents(){
        return studentReprository.findAll();
    }


    public Student saveStudent(Student student){
        return studentReprository.save(student);
    }

    public void deleteStudent(Student student){
        studentReprository.delete(student);
    }

    public Student getStudentById(Long id)throws ResourceNotFoundException {
        return studentReprository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Student not found for this id :: " + id));
    }

    public Student addSubjectToStudentService(Long studentId,Long subjectId) throws ResourceNotFoundException {
        Subject subject= subjectService.getSubjectById(subjectId);
        Student student=getStudentById(studentId);
        student.addSub(subject);
        return saveStudent(student);
    }


    public List<Student> deleteStudentService(Long id) throws ResourceNotFoundException {
        Student student=getStudentById(id);
        deleteStudent(student);
        return getStudents();
    }

    public Student updateStudentService(Long id, Student std) throws ResourceNotFoundException {
        Student student=getStudentById(id);
        student.setName(std.getName());
        student.setDob(std.getDob());
        student.setEmail(std.getEmail());
        return saveStudent(student);
    }
}
