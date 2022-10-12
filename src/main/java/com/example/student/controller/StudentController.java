package com.example.student.controller;


import com.example.student.exception.ResourceNotFoundException;
import com.example.student.reprository.SubjectReprository;
import com.example.student.model.Student;
import com.example.student.reprository.StudentReprository;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentReprository studentReprository;

    private final SubjectReprository subjectReprository;

    @Autowired
    public StudentController(StudentService studentService, StudentReprository studentReprository ,SubjectReprository subjectReprository) {
        this.studentService = studentService;
        this.studentReprository = studentReprository;
        this.subjectReprository = subjectReprository;
    }

    @GetMapping
    public List<Student> getStudents(){
          return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(
            @PathVariable Long id
    ) throws ResourceNotFoundException {
        return studentService.getStudentById(id);
    }


    @PostMapping()
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    //manytomany
    @PutMapping("/{studentId}/subject/{subjectId}")
    public Student addSubToStudent(
            @PathVariable Long studentId,
            @PathVariable Long subjectId
    ){
        return studentService.addSubjectToStudentService(studentId,subjectId);
    }

    //delete student
    @DeleteMapping("/{id}")
    public List<Student> deleteStudent(
            @PathVariable Long id
    ) throws ResourceNotFoundException {
        return studentService.deleteStudentService(id);
    }

    //update student by patch
    @PatchMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student std
    ) throws ResourceNotFoundException {
          return studentService.updateStudentService(id,std);
    }
}