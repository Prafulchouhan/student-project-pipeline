package com.example.student.service;


import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Subject;
import com.example.student.reprository.StudentReprository;
import com.example.student.model.Student;
import com.example.student.reprository.SubjectReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentReprository studentReprository;

    private final SubjectReprository subjectReprository;
    private final SubjectService subjectService;

    @Autowired
    public StudentService(StudentReprository studentReprository, SubjectReprository subjectReprository, SubjectService subjectService) {
        this.studentReprository = studentReprository;
        this.subjectReprository = subjectReprository;
        this.subjectService = subjectService;
    }

    public List<Student> getStudents(){
        return studentReprository.findAll();
    }


    public Student saveStudent(Student student){
        return studentReprository.save(student);
    }



    public Student getStudentById(Long id)throws ResourceNotFoundException {
        return studentReprository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Student not found for this id :: " + id));
    }

    public Student addSubjectToStudentService(Long studentId,Long subjectId) throws ResourceNotFoundException {
        Subject subject= subjectReprository.findById(subjectId).get();
        Student student= studentReprository.findById(studentId).get();
        student.addSub(subject);
        return studentReprository.save(student);
    }


    public void deleteStudentService(Long id) throws ResourceNotFoundException {
            studentReprository.deleteById(id);
    }

    public Student updateStudentService(Long id, Map<Object, Object> fields) throws ResourceNotFoundException {
        Student student=studentReprository.findById(id).get();

        fields.forEach((k,v) ->{
            Field field=ReflectionUtils.findField(Student.class,String.valueOf(k));
            field.setAccessible(true);
            ReflectionUtils.setField(field, student, v);
        });
        return studentReprository.save(student);

    }
}
