package org.example;

import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    private Service service;
    private StudentXMLRepo studentRepo;

    @BeforeEach
    public void setUp() {
        studentRepo = new StudentXMLRepo("src/resources/Studenti2.xml");
        StudentValidator studentValidator = new StudentValidator();
        service = new Service(studentRepo, studentValidator, null, null, null, null);
    }

    @Test
    void testAddStudent_Success() {

        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testAddStudent_Failure_ExistingStudent() {

        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        Student result;

        result = service.findStudent("1");
        System.out.println(result);

        result = service.addStudent(student); // Add the student once
        assertEquals(student, result);
        result = service.addStudent(student);
        Assertions.assertNull(result);

        service.deleteStudent("1");
    }

    @Test
    void testGroupGreaterThan0_Success() {
        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testGroupGreaterThan0_Failure() {
        Student newStudent = new Student("1", "John Doe", -3, "john.doe@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void testNumeNonEmpty_Success() {
        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testNumeNonEmpty_Failure() {
        Student newStudent = new Student("1", "", -3, "john.doe@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void testNumeNonNull_Success() {
        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testNumeNonNull_Failure() {
        Student newStudent = new Student("1", null, -3, "john.doe@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void testIdNonEmpty_Failure() {
        Student newStudent1 = new Student("", "John Doe", 123, "john.doe@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent1));
    }

    @Test
    void testIdNonEmpty_Success() {
        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testIdNonNull_Failure() {
        Student newStudent1 = new Student(null, "John Doe", 123, "john.doe@example.com");
        assertThrows(NullPointerException.class, () -> this.service.addStudent(newStudent1));
    }

    @Test
    void testIdNonNull_Success() {
        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testEmailNonEmpty_Failure() {
        Student newStudent1 = new Student("1", "John Doe", 123, "");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent1));
    }

    @Test
    void testEmailNonEmpty_Success() {
        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testEmailNonNull_Failure() {
        Student newStudent1 = new Student("1", "John Doe", 123, null);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent1));
    }

    @Test
    void testEmailNonNull_Success() {
        Student result;
        Student student = new Student("1", "John Doe", 123, "john.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("1");
    }

    @Test
    void testValidate_InvalidDeadline_Failure(){
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("123", "test", 15, 5);
        assertThrows(ValidationException.class, () -> validator.validate(t));
    }

    @Test
    void testValidate_ValidDeadline_Success(){
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("123", "test", 14, 5);
        Assertions.assertDoesNotThrow(() -> validator.validate(t));
        assertEquals(t.getDeadline(), 14);
    }

    @Test
    void testTemaDescriereNonEmpty_Failure() {
        TemaValidator validator = new TemaValidator();
        Tema newTema = new Tema("1", "", 7, 6);
        assertThrows(ValidationException.class, () -> validator.validate(newTema));
    }

    @Test
    void testTemaDescriereNonEmpty_Success() {
        TemaValidator validator = new TemaValidator();
        Tema newTema = new Tema("1", "SomeDescription", 7, 6);  // Passing a non-empty Descriere
        assertDoesNotThrow(() -> validator.validate(newTema));
    }

    @Test
    void testTemaIDEmpty_Failure() {
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("", "test", 5, 5);
        assertThrows(ValidationException.class, () -> validator.validate(t));
    }

    @Test
    void testTemaIDNull_Failure() {
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema(null, "test", 5, 5);
        assertThrows(ValidationException.class, () -> validator.validate(t));
    }

    @Test
    void testDeadlineLowerThanRange_Failure() {
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("123", "test", 0, 5);
        assertThrows(ValidationException.class, () -> validator.validate(t));
    }

    @Test
    void testDeadlineHigherThanRange_Failure() {
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("123", "test", 15, 5);
        assertThrows(ValidationException.class, () -> validator.validate(t));
    }

    @Test
    void testPrimireLowerThanRange_Failure() {
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("123", "test", 5, 0);
        assertThrows(ValidationException.class, () -> validator.validate(t));
    }

    @Test
    void testPrimireHigherThanRange_Failure() {
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("123", "test", 5, 15);
        assertThrows(ValidationException.class, () -> validator.validate(t));
    }

    @Test
    void testValidTema_Success() {
        TemaValidator validator = new TemaValidator();
        Tema t = new Tema("123", "test", 5, 5);
        assertDoesNotThrow(() -> validator.validate(t));
    }
}
