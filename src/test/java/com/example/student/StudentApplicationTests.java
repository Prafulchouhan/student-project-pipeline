package com.example.student;

import com.example.student.exception.ResourceNotFoundException;
import com.example.student.model.Student;
import com.example.student.reprository.StudentReprository;
import com.example.student.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentApplicationTests {

//	@Autowired
//	private StudentService studentService;
//	@MockBean
//	private StudentReprository studentReprository;
//
//
//	@Test
//	@Order(1)
//	@DisplayName("Test getStudent")
//	void getStudentTest() {
////		List<Student> StudentList={("praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,3))};
//		when(studentReprository.findAll()).thenReturn(
//				List.of(new Student("praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,3)),
//						new Student("pranjal","pranjal@gmail.com", LocalDate.of(2001, Month.JANUARY,5)) )
//						.stream().collect(Collectors.toList()));
//				assertEquals(2,studentService.getStudents().size());
//
//	}
////	@Test
////	void getStudentById(){
////		Long id= 8L;
////		when(studentReprository.findById(id).get()).thenReturn(
////				List.of(new Student("praful","praful@gmail.com", LocalDate.of(2002, Month.JANUARY,3)))
////						.collect(Collectors.toList()));
////		assertEquals(2,studentService.getStudents().size());
////	}
//
//	@Test
//	@Order(2)
//	@DisplayName("Test getStudentByStudentId")
//	public void test_getEmployeeByEmployeeId() throws ResourceNotFoundException {
//
//		Long id = 1L;
//		Student s1ForMock = new Student("praful", "praful@gmail.com", LocalDate.of(2002,Month.JANUARY,1));
//		doReturn(Optional.of(s1ForMock)).when(studentReprository).findById(id);
//		Student s1ByService = studentService.getStudentById(id);
//		assertNotNull(s1ByService,"Employee with employeeId : "+id+" not found");
////		assertEquals(id,s1ByService.getId());
//		assertEquals(s1ForMock.getName(), s1ByService.getName());
//		assertEquals(s1ForMock.getAge(), s1ByService.getAge());
//		assertEquals(s1ForMock.getEmail(), s1ByService.getEmail());
//	}

}
