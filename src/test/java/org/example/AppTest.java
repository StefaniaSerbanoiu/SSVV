package org.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import validation.NotaValidator;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    private Service service;
    private StudentXMLRepo studentRepo;
    private TemaXMLRepo temaXMLRepo;
    private NotaXMLRepo notaXMLRepo;


    @BeforeEach
    public void setUp() {
        studentRepo = new StudentXMLRepo("src/resources/Studenti2.xml");
        temaXMLRepo = new TemaXMLRepo("src/resources/Teme2.xml");
        notaXMLRepo = new NotaXMLRepo("src/resources/Note2.xml");

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        NotaValidator notaValidator = new NotaValidator(studentRepo, temaXMLRepo);

        service = new Service(studentRepo, studentValidator, temaXMLRepo, temaValidator, notaXMLRepo, notaValidator);
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

    @Test
    void testAddStudent2_Success() {
        Student result;
        Student student = new Student("2", "Jane Doe", 123, "jane.doe@example.com");

        result = service.addStudent(student);
        assertEquals(student, result);

        service.deleteStudent("2");
    }

    @Test
    void testAddAssignment_Success() {
        TemaValidator validator = new TemaValidator();
        Tema newTema = new Tema("1", "prima tema", 7, 6);  // Passing a non-empty Descriere
        assertDoesNotThrow(() -> validator.validate(newTema));
    }

    @Test
    void testAddGrade_Success() {
        Double notaDouble;
        Student student = new Student("3", "Jane Doe", 123, "jane.doe@example.com");
        service.addStudent(student);
        Nota nota = new Nota("1", "3", "1", 10, LocalDate.now());

        notaDouble = service.addNota(nota, "good");
        assertEquals(nota.getNota(), notaDouble);
    }

    @Test
    void testIntegration_Success() {
        testAddStudent2_Success();
        testAddAssignment_Success();
        testAddGrade_Success();
    }

    // A4 --> TH

    @Test
    void testAddStudent() {
        Student result;
        Student student2 = new Student("4", "Jack Doe", 200, "jack@doe.com");

        result = service.addStudent(student2);
        assertEquals(student2, result);

        service.deleteStudent("4");
    }

    @Test
    void testAddAssignment() {
        TemaValidator validator = new TemaValidator();
        Tema newTema = new Tema("4", "a4-ssvv", 12, 8);
        assertDoesNotThrow(() -> validator.validate(newTema));
    }

    @Test
    void testIntegrationAddAssignment() {
        testAddStudent();
        testAddAssignment();
    }

    @Test
    void testAddGrade() {
        Double notaDouble;
        Student student = new Student("4", "Jack Doe", 200, "jack@doe.com");
        service.addStudent(student);
        Nota nota = new Nota("1", "4", "4", 9, LocalDate.now());

        notaDouble = service.addNota(nota, "meh");
        assertEquals(nota.getNota(), notaDouble);

        service.deleteStudent("4");
    }

    @Test
    void testIntegrationAddGrade() {
        testAddStudent();
        testAddAssignment();
        testAddGrade();
    }
}
