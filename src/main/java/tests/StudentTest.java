package tests;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StudentTest {

    private Service service;
    private StudentXMLRepo studentRepo;

    @BeforeEach
    public void setUp() {
        studentRepo = new StudentXMLRepo("src/resources/Studenti2.xml");
        StudentValidator studentValidator = new StudentValidator();
        service = new Service(studentRepo, studentValidator, null, null, null, null);
    }

    @Test
    public void testAddStudent_Success() {
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        Student result = service.addStudent(student);

        assertEquals(student, result);

        service.deleteStudent(student.getID());
    }

    @Test
    public void testAddStudent_Failure_ExistingStudent() {

        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        Student result;

        result = service.addStudent(student);
        assertEquals(student, result);

        result = service.addStudent(student);
        assertNull(result);

        service.deleteStudent(student.getID());
    }
}