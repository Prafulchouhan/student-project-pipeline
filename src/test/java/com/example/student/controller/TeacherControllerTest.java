package com.example.student.controller;

import com.example.student.model.Subject;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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

    Teacher t1= Teacher.builder().id(1L).name("Adi").build();
    Teacher t2=Teacher.builder().id(1L).name("Adi").build();
    Teacher t3=Teacher.builder().id(1L).name("Adi").build();

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
                .andExpect(jsonPath("$[2].name", is("Adi")));
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
                .andExpect(jsonPath("$.name", is("Adi")));
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
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void saveTeacher() throws Exception {
        doReturn(t1).when(teacherService).createTeacherService(any());
        String content = new ObjectMapper().writeValueAsString(t1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/teacher")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(t1.getName())))
                .andReturn().getResponse().getContentAsString();
    }
}