package com.example.student.service;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Teacher;
import com.example.student.reprository.TeacherReprository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {

    private MockMvc mockMvc;

    Teacher t1=new Teacher(1L,"abhi");
    Teacher t2=new Teacher(1L,"abhi");
    Teacher t3=new Teacher(1L,"abhi");
    @Mock
    private TeacherReprository teacherReprository;

    @InjectMocks
    private TeacherService teacherService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(teacherService).build();
    }

    @Test
    public void getAllStudent(){
        Mockito.when(teacherReprository.findAll()).thenReturn(Arrays.asList(t1,t2,t3));
        System.out.println(teacherService.getTeachersService());
        assertThat(teacherService.getTeachersService().size()).isEqualTo(3);
    }


    @Test
    public void getAllSubject() throws ResourceNotFoundException {
        long id = 1L;
        Mockito.when(teacherReprository.findById(id)).thenReturn(Optional.ofNullable(t1));
        assertThat(teacherService.getTeacherById(t1.getId()).getId()).isEqualTo(1L);
    }

    @Test
    public void saveStudent() throws Exception{
        Teacher record=new Teacher(1L,"Adi");
        Mockito.when(teacherReprository.save(record)).thenReturn(record);
        assertThat(teacherService.createTeacherService(record).getId()).isEqualTo(1);
    }

    @Test
    public void deleteTeacher() throws ResourceNotFoundException {
        teacherService.deleteTeacherService(anyLong());
        verify(teacherReprository,times(1)).deleteById(anyLong());
    }

    @Test
    public void getDepartmentById_throwsException(){
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> teacherService.getTeacherById(1L))
                .withMessage("Teacher not found for this id :: 1");
    }
}



