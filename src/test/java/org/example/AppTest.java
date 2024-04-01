package org.example;

import domain.Student;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest{

    private Service service;
    private StudentXMLRepo studentRepo;

    @BeforeEach
    public void setUp() {
        studentRepo = new StudentXMLRepo("src/resources/Studenti2.xml");
        StudentValidator studentValidator = new StudentValidator();
        service = new Service(studentRepo, studentValidator, null, null, null, null);
    }

    @org.junit.jupiter.api.Test
    public void testAddStudent_Success() {

        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    public void testAddStudent_Failure_ExistingStudent() {

        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        Student result;

        result = service.findStudent("1");
        System.out.println(result);

        result = service.addStudent(student); // Add the student once
        assertEquals(student, result);
        result = service.addStudent(student);
        assertEquals(null, result);

        service.deleteStudent("1");
    }
}
