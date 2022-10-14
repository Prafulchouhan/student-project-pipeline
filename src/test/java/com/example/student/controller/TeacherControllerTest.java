package com.example.student.controller;

import com.example.student.model.Teacher;
import com.example.student.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
public class TeacherControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    Teacher t1=new Teacher(1L,"abhi");
    Teacher t2=new Teacher(1L,"abhi");
    Teacher t3=new Teacher(1L,"abhi");

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    public void getAllTeacher() throws Exception {
        List<Teacher> teachers = new ArrayList<>(Arrays.asList(t1,t2,t3));
        Mockito.when(teacherService.getTeachersService()).thenReturn(teachers);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("abhi")));
    }

    @Test
    public void getTeachertById() throws Exception {
        Mockito.when(teacherService.getTeacherById(t1.getId())).thenReturn(t1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("abhi")));
    }

    @Test
    public void deleteTeacher()throws Exception{

//        List<Teacher> teachers = new ArrayList<>(Arrays.asList(t1,t2,t3));
//        Mockito.when(teacherService.deleteTeacherService(t1.getId())).thenReturn(teachers);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("api/v1/teacher/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$",hasSize(2)));


        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/teacher/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
            .andExpect(status().isOk());
    }
    @Test
    public void postTeacher() throws Exception {

//        Mockito.when(teacherService.createTeacherService(t1)).thenReturn(t1);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new Teacher(1L,"abhi")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void saveTeacher() throws Exception {
        Mockito.when(teacherService.createTeacherService(t1)).thenReturn(t1);
        String content = objectWriter.writeValueAsString(t1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/v1/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1L)));
    }
}