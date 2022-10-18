package com.example.student.controller;


import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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
    public ResponseEntity<Student> registerNewStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.saveStudent(student),HttpStatus.CREATED);
    }

    //manytomany
    @PutMapping("/{studentId}/subject/{subjectId}")
    public Student addSubToStudent(
            @PathVariable Long studentId,
            @PathVariable Long subjectId
    ) throws ResourceNotFoundException {
        return studentService.addSubjectToStudentService(studentId,subjectId);
    }

    //delete student
    @DeleteMapping("/{id}")
    public void deleteStudent(
            @PathVariable Long id
    ) throws ResourceNotFoundException {
         studentService.deleteStudentService(id);
    }

    //update student by patch
    @PatchMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Map<Object,Object> fields
    ) throws ResourceNotFoundException {
          return new ResponseEntity<>(studentService.updateStudentService(id,fields), HttpStatus.OK);
    }
}
