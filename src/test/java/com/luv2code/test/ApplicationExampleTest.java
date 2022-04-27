package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

//It uses main Spring boot app
@SpringBootTest(classes= MvcTestingExampleApplication.class)
public class ApplicationExampleTest {
	
	private int count = 0;
	
	@Value("${info.app.name}")
	private String appInfo;
	
	@Value("${info.app.description}")
	private String appDescription;
	
	@Value("${info.app.version}")
	private String appVersion;
	
	@Value("${info.school.name}")
	private String schoolName;
	
	@Autowired
	CollegeStudent student;
	
	@Autowired
	StudentGrades studentGrades;
	
	@Autowired
	ApplicationContext context;
	
	@BeforeEach
	public void beforeEach()
	{
		count++;
		System.out.println("Testing: " + appInfo + " which is "+ appDescription +
				" version: " + appVersion + ", Execution count: " + count);
		
		student.setFirstname("Eric");
		student.setLastname("Roby");
		student.setEmailAddress("roby@citalin.com");
		studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0,85.0,76.50,91.75)));
		student.setStudentGrades(studentGrades);
	}
	
	
	@Test
	@DisplayName("Add grade results for student grades")	
	void addGradeResultsForStudentsGrades()
	{
		assertEquals(353.25,studentGrades.addGradeResultsForSingleClass(
				student.getStudentGrades().getMathGradeResults()));
	}
	
	@Test
	@DisplayName("Add grade results for student grades not equal")	
	void addGradeResultsForStudentsGradesAssertNotEquals()
	{
		assertNotEquals(0,studentGrades.addGradeResultsForSingleClass(
				student.getStudentGrades().getMathGradeResults()));
	}
	
	@Test
	@DisplayName("Is grade greater")
	public void isGradeGreaterStudentGrades()
	{
		assertTrue(studentGrades.isGradeGreater(90, 75),"Failure - Should be true");
	}
	
	@DisplayName("Is grade greater false")
	@Test
	public void isGradeGreaterStudentGradesAssertFalse()
	{
		assertFalse(studentGrades.isGradeGreater(89, 90),"Failure - should be false");
	}
	
	@DisplayName("Check Null for student grades")
	@Test
	public void checkNullForStudentGrades()
	{
		assertNotNull(studentGrades.checkNull(student.getStudentGrades()
				.getMathGradeResults()),"Object should not be null");
	}
	
	@DisplayName("Create Student without grades init")
	@Test
	public void createStudentWithoutGrades()
	{
		CollegeStudent studentTwo = context.getBean("collegeStudent",CollegeStudent.class);
		studentTwo.setFirstname("Chad");
		studentTwo.setLastname("Darby");
		studentTwo.setEmailAddress("chad.darby@luv2code_school.com");
		assertNotNull(studentTwo.getFirstname());
		assertNotNull(studentTwo.getLastname());
		assertNotNull(studentTwo.getEmailAddress());
		assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));		
		
	}
	
	@DisplayName("Verify students are prototypes")
	@Test
	public void verifyStudentsArePrototypes()
	{
		CollegeStudent studentTwo = context.getBean("collegeStudent",CollegeStudent.class);
		assertNotSame(student,studentTwo);
	}
	
	@DisplayName("Find Grade Point Avarage")
	@Test
	public void findGradePointAverage()
	{
		assertAll("Testing all assertEquals",
				() -> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
						student.getStudentGrades().getMathGradeResults()
						)),
				() -> assertEquals(88.31,studentGrades.findGradePointAverage(
						student.getStudentGrades().getMathGradeResults()
						))				
				);
		
	}

}
