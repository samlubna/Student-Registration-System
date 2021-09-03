package Tests;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ncl.csc8404.coursework.studentManagement.Name;
import uk.ac.ncl.csc8404.coursework.studentManagement.Student;
import uk.ac.ncl.csc8404.coursework.studentManagement.StudentManager;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    static Student s,s2,s3;
    static StudentManager manager = StudentManager.getInstance();

    // This method instantiates all the students being used in the tests
    @BeforeClass
    public static void studentTester () {
     // Setting the date using the LocalDate object
     LocalDate local = LocalDate.parse("1997-09-06");
     // Converting it to a date object
     Date dt = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
     // Setting the names
     Name nm = new Name("Russel","Wilson");
     Name nm1 = new Name("Bean","Wilson");
     Name nm2 = new Name("Lean","Wilson");
     // Creating the students
     s = manager.createStudent(nm,dt,"PGT");
     s2 = manager.createStudent(nm1,dt,"PGR");
     s3 = manager.createStudent(nm2,dt,"UG");
    }

    @Test
    public void getStudentID() {
        // Attempting to retrieve the studentID of an unregistered student
        assertThrows(NullPointerException.class,
                ()-> s.getStudentID());
    }

    @Test
    public void getStudentType() {
        /* Attempting to retrieve a student's type. The return value is
        a string */
        assertEquals("PGT",s.getStudentType());
    }

    @Test
    public void listModules() {
        /* Attempting to list the modules of a student who hasn't been registered
        for any modules, or even to the university for that matter. This should
        return a message*/
        assertFalse(s.listModules() == null);

    }

    @Test
    public void enoughCredits() {
        /* An unregistered student */
        assertFalse(s.hasCard());

        /* A registered student */
        manager.registerStudent(s);
        manager.amendStudentData(s.getStudentID(),"CSC8404, ADv Java, 180");
        assertTrue(s.enoughCredits());
        // Uncomment the line below to view the smart card info
       // System.out.println(s.getStudentInfo());

    }

    @Test
    public void returnName() {
        /* retrieving the name of the student*/
        assertEquals("Russel Wilson",s.returnName().toString());
    }

    @Test
    public void hasCard() {
         /* Returns a boolean based on the state smartCard. Before registering*/
        assertFalse(s2.hasCard());

        /* After registering */
        manager.registerStudent(s2);
        assertTrue(s2.hasCard());
    }

    @Test
    public void getStudentInfo() {
        /* Trying to retrieve the studentInfo of an unregistered student */
        assertThrows(NullPointerException.class,
                ()->s3.getStudentInfo());

        /* Trying to retrieve the studentInfo of a registered student
         who hasn't applied to any modules. It should print out the smart card info
         , and call the listModules method*/
        manager.registerStudent(s3);
        assertTrue(s3.getStudentInfo()!=null);
        //System.out.println(s3.getStudentInfo());

    }
}