package com.example.student.service;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Student;
import com.example.student.model.Subject;
import com.example.student.model.Teacher;
import com.example.student.reprository.StudentReprository;
import com.example.student.reprository.SubjectReprository;
import com.example.student.reprository.TeacherReprository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {

    private MockMvc mockMvc;

    Subject s1=Subject.builder().id(1L).name("math").build();
    Subject s2=Subject.builder().id(2L).name("math").build();
    Subject s3=Subject.builder().id(3L).name("math").build();

    Teacher t1=Teacher.builder().id(1L).name("Adi").build();
    @Mock
    private SubjectReprository subjectReprository;
    @Mock
    private TeacherReprository teacherReprository;

    @InjectMocks
    private SubjectService subjectService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(subjectService).build();
    }

    @Test
    public void getAllSubject(){
        when(subjectReprository.findAll()).thenReturn(Arrays.asList(s1,s2,s3));
        System.out.println(subjectService.getSubjectService());
        assertThat(subjectService.getSubjectService().size()).isNotNull();
    }


    @Test
    public void getSubjectByid() throws ResourceNotFoundException {
        long id = 1L;
        when(subjectReprository.findById(s1.getId())).thenReturn(Optional.ofNullable(s1));
        assertThat(subjectService.getSubjectById(s1.getId()).getName()).isEqualTo("Maths");
    }

    @Test
    public void saveSubject() throws Exception{
        Subject record=Subject.builder().id(1L).name("math").build();
        when(subjectReprository.save(record)).thenReturn(record);
        assertThat(subjectService.createSubjetService(record).getId()).isEqualTo(1);
    }

    @Test
    public void deleteSubject() throws ResourceNotFoundException {
        subjectService.deleteSubjectService(anyLong());
        verify(subjectReprository,times(1)).deleteById(anyLong());
    }

    @Test
    public void assignTeacher() throws ResourceNotFoundException {
        when(subjectReprository.findById(anyLong())).thenReturn(Optional.ofNullable(s1));
        when(teacherReprository.findById(anyLong())).thenReturn(Optional.ofNullable(t1));
        when(subjectReprository.save(any(Subject.class))).thenReturn(s1);

        assertThat(subjectService.assignTeacherToSubjectService(1L,1L)).isNotNull().isEqualTo(s1);
        verify(subjectReprository, times(1)).save(any(Subject.class));

    }
    @Test
    public void getSubjectByIdNotFoundException(){
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> subjectService.getSubjectById(1L))
                .withMessage("Subject not found for this id :: 1");
    }
}



