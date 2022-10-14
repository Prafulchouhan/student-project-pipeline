package com.example.student.service;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Student;
import com.example.student.model.Subject;
import com.example.student.reprository.StudentReprository;
import com.example.student.reprository.SubjectReprository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;



//@RunWith(MockitoJUnitRunner.class)
public class studentServiceTest {

    private MockMvc mockMvc;

    Student s1=new Student(1L,"praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,01));

    Student s2=new Student(2L,"praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,01));

    Student s3=new Student(3L,"praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,01));

    Subject sub=new Subject(1L,"Maths");
    @Mock
    private StudentReprository studentReprository;

    @Mock
    private SubjectReprository subjectReprository;

    @InjectMocks
    private StudentService studentService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentService).build();
    }

    @Test
    public void getAllStudent(){
        Mockito.when(studentReprository.findAll()).thenReturn(Arrays.asList(s1,s2,s3));
        assertThat(studentService.getStudents().size()).isEqualTo(3);
    }

    @Test
    public void getStudentById() throws ResourceNotFoundException {
        long id = 1L;
        Mockito.when(studentReprository.findById(id)).thenReturn(Optional.ofNullable(s1));
        assertThat(studentService.getStudentById(id).getName()).isEqualTo("praful");
    }

    @Test
    public void saveStudent() throws Exception{
        Student record = new Student(1L,"praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,01));
        Mockito.when(studentReprository.save(record)).thenReturn(record);
        assertThat(studentService.saveStudent(record).getName()).isEqualTo("praful");
    }
    @Test
    public void updateStudent() throws ResourceNotFoundException {
        Mockito.when(studentReprository.findById(anyLong())).thenReturn(Optional.ofNullable(s1));
        Mockito.when(studentReprository.save(any(Student.class))).thenReturn(s1);
        assertThat(studentService.updateStudentService(anyLong(),s1)).isNotNull();
    }
    @Test
    public void deleteStudent() throws ResourceNotFoundException {
        studentService.deleteStudentService(anyLong());
        verify(studentReprository,times(1)).deleteById(anyLong());
    }
    @Test
    public void addSubjectToStudentService() throws ResourceNotFoundException {
        when(subjectReprository.findById(anyLong())).thenReturn(Optional.ofNullable(sub));
        when(studentReprository.findById(anyLong())).thenReturn(Optional.ofNullable(s1));
        when(studentReprository.save(any(Student.class))).thenReturn(s1);

        assertThat(studentService.addSubjectToStudentService(anyLong(),anyLong())).isNotNull();
        verify(studentReprository, times(2)).save(any(Student.class));
    }

//    @Test
//    public void deleteStudent() {
//        long id = 1L;
//        Mockito.when(studentReprository.delete(s1)).thenReturn(Optional.ofNullable(s1));
//        assertThat(studentService.getStudentById(id).getName()).isEqualTo("praful");
//    }

    @Test
    public void getStudentByIdNotFoundException(){
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> studentService.getStudentById(1L))
                .withMessage("Student not found for this id :: 1");
    }

}



