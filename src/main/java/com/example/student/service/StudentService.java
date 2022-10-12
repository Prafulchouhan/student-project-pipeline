package com.example.student.service;


import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Subject;
import com.example.student.reprository.StudentReprository;
import com.example.student.model.Student;
import com.example.student.reprository.SubjectReprository;
import com.example.student.reprository.TeacherReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentReprository studentReprository;

    private final SubjectReprository subjectReprository;

    private final TeacherReprository teacherReprository;

    @Autowired
    public StudentService(StudentReprository studentReprository, SubjectReprository subjectReprository, TeacherReprository teacherReprository) {
        this.studentReprository = studentReprository;
        this.subjectReprository = subjectReprository;
        this.teacherReprository = teacherReprository;
    }

    public List<Student> getStudents(){
        return studentReprository.findAll();
    }


    public void addNewStudent(Student student) {
        studentReprository.save(student);
    }
    public Student addSubjectToStudentService(Long studentId,Long subjectId){
        Subject subject=subjectReprository.findById(subjectId).get();
        Student student=studentReprository.findById(studentId).get();
        student.addSub(subject);
        return studentReprository.save(student);
    }

    public Student getStudentById(Long id)throws ResourceNotFoundException {
        return studentReprository.findById(id)
                .orElseThrow(()->
                new ResourceNotFoundException("Student not found for this id :: " + id));
    }
    public List<Student> deleteStudentService(Long id) throws ResourceNotFoundException {
        Student student=getStudentById(id);
        studentReprository.delete(student);
        return studentReprository.findAll();
    }

    public Student updateStudentService(Long id, Student std) throws ResourceNotFoundException {
        Student student=getStudentById(id);
        student.setName(std.getName());
        student.setDob(std.getDob());
        student.setEmail(std.getEmail());
        return studentReprository.save(student);
    }
}
