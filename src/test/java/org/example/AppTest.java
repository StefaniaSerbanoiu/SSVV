package org.example;

import domain.Student;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Before;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    private Service service;
    private StudentXMLRepo studentRepo;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    @org.junit.Test
    public void testApp() {
        assertTrue(true);
    }

    @Before
    public void setUp() {
        studentRepo = new StudentXMLRepo("src/resources/Studenti2.xml");
        StudentValidator studentValidator = new StudentValidator();
        service = new Service(studentRepo, studentValidator, null, null, null, null);
    }

    @org.junit.Test
    public void testAddStudent_Success() {
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        Student result = service.addStudent(student);

        assertEquals(student, result);

        service.deleteStudent(student.getID());
    }

    @org.junit.Test
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
