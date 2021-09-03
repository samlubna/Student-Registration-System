package uk.ac.ncl.csc8404.coursework.Manager;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class StudentManagerTest {
    static StudentManager manager = StudentManager.getInstance();
    static int studentCounter = -1;

    @Test
    public void createStudentTest() {
        // All of the tests in this method evaluate the StudentFactory class.

        /*Providing null parameters to the function. The method should return a NullPointerException
        regardless of which input is null.*/
        assertThrows(NullPointerException.class,
                ()->manager.createStudent(null,null,null));

        /*Providing an invalid student type 'LMP' */
        Name nm = new Name("Mac","Johnson");
        LocalDate local = LocalDate.parse("1997-09-06");
        Date dt = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
         assertThrows(IllegalArgumentException.class,
                 ()->manager.createStudent(nm, dt, "LMP"));


         /* Providing a student who is too young for the program being applied to*/
        Name nm2 = new Name("James","Mathews");
        LocalDate local2 = LocalDate.parse("2006-09-06");
        Date dt2 = Date.from(local2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertThrows(IllegalArgumentException.class,
                ()-> manager.createStudent(nm2, dt2, "PGR"));

    }

    @Test
    public void registerStudentTest() {

        /* Duplicate registration test. The uniqueness of a student is based solely on the first and last names.
        So if two students are generated with the same name, then both will be represented by the same
        student object, i.e., the same studentID*/
        Name nm = new Name("Aslam","Chaudry");
        LocalDate local = LocalDate.parse("1990-09-06");
        Date dt = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Student s1 = manager.createStudent(nm,dt,"PGR");
        Student s2 = manager.createStudent(s1.returnName(),s1.returnBirthDate(),s1.getStudentType());
        manager.registerStudent(s1);
        assertTrue(s1.getStudentID()==s2.getStudentID());
        assertThrows(IllegalArgumentException.class,()->manager.registerStudent(s2));


        /* Re-registering a terminated student. There is no formal 'termination' of a student.
         The terminateStudent function only removes the student data from the university's 'database' */
        Name nm2 = new Name("Suliman","Foxen");
        LocalDate local2 = LocalDate.parse("1999-09-06");
        Date dt2 = Date.from(local2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Student s3 = manager.createStudent(nm2,dt2,"UG");
        manager.registerStudent(s3);
        manager.terminateStudent(s3.getStudentID());
        assertThrows(IllegalArgumentException.class,()->manager.registerStudent(s3));


        /*Providing a null input to the function. Throws a null exception with a
        custom message*/
        Student s4 = null;
        assertThrows(NullPointerException.class,()->manager.registerStudent(s4));

    }

    @Test
    public void noOfStudentsTest(){

        /* The function returns the number of students, based on the type provided, that are
         currently enrolled at the university*/
        Name nm = new Name("Mustafah","Ali");
        LocalDate local = LocalDate.parse("1992-09-06");
        Date dt = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Student s1  = manager.createStudent(nm,dt,"PGT");
        assertEquals(0,manager.noOfStudents(s1.getStudentType()));
        manager.registerStudent(s1);
        assertEquals(1,manager.noOfStudents(s1.getStudentType()));

        /*Providing a null input*/
        assertThrows(NullPointerException.class,
                ()-> manager.noOfStudents(null));

        /*Providing an invalid type NLM*/
        assertThrows(IllegalArgumentException.class,
                ()-> manager.noOfStudents("NLM"));
    }

    @Test
    public void amendStudentDataTest(){
        /* Providing null inputs to the function */
        Name nm = new Name("Paul","Levasquez");
        LocalDate local = LocalDate.parse("1989-09-06");
        Date dt = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Student s1 = manager.createStudent(nm,dt,"UG");
        manager.registerStudent(s1);
        assertThrows(NullPointerException.class,
                ()->  manager.amendStudentData(s1.getStudentID(),null));

        /* Trying to amend the studentRecord of a terminated student. */
        Name nm2 = new Name("Mark","Callaway");
        LocalDate local2 = LocalDate.parse("1985-09-06");
        Date dt2 = Date.from(local2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Student s2 = manager.createStudent(nm2,dt2,"PGR");
        manager.registerStudent(s2);
        manager.terminateStudent(s2.getStudentID());
        assertThrows(NullPointerException.class,
                ()-> manager.amendStudentData(s2.getStudentID(),"John Abrams"));

        /* Attempting to amend the student record of an unregistered student. Since an unregistered
        student will not have an ID, the function should throw an NullPointerException at the
        getStudentID method.*/
        Name nm3 = new Name("Pual","Heyman");
        LocalDate local3 = LocalDate.parse("1988-09-06");
        Date dt3 = Date.from(local3.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Student s3 = manager.createStudent(nm3,dt3,"PGT");
            assertThrows(NullPointerException.class,
                ()-> manager.amendStudentData(s3.getStudentID(),"CSC45, Advanced JPG, 20"));

        /* Providing the wrong type of input for a student */
        Name nm4 = new Name("Belt","Malone");
        LocalDate local4 = LocalDate.parse("1982-09-06");
        Date dt4 = Date.from(local4.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Student s4 = manager.createStudent(nm4,dt4,"PGR");
        manager.registerStudent(s4);
        assertThrows(IllegalArgumentException.class,
                ()-> manager.amendStudentData(s4.getStudentID(),"CS45, JackEng, 90"));

        /* Providing the right type of input for a student*/
        manager.amendStudentData(s4.getStudentID(),"Carl Segan");
        assertEquals("Supervisor: "+"Carl Segan",
                s4.listModules());

    }
    
}