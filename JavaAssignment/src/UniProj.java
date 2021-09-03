import uk.ac.ncl.csc8404.coursework.Manager.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// The University's Management System
public class UniProj {

    // Various lists to store the file data
    static List<Name> studentList = new ArrayList<>();
    static List<Date> dateList = new ArrayList<>();
    static List<String> courseModuleList = new ArrayList<>();
    static List<String> supervisorList = new ArrayList<>();

    // Counters to keep track of the list entries
    static int studentCounter = -1;
    static int moduleCounter = -1;

    // Instance of the StudentManager to handle the students
    static StudentManager manager = StudentManager.getInstance();

    public static void main(String[] args) throws FileNotFoundException
    {
//        prepareStudentAndDates();
//        Student s1 = generateNextStudentInfo("PGR");
//        manager.registerStudent(s1);
//        manager.amendStudentData(s1.getStudentID(),getModule("PGR"));
//        manager.amendStudentData(s1.getStudentID(),getModule(s1.getStudentType()));
//        System.out.println(s1.getStudentInfo());


    }

    /* A method that generates a new student from the data read from the files.
     It takes a student type as an input. Since this solely for automating the
     student creation process, no exceptions have been handled */
    public static Student generateNextStudentInfo (String studentType){
        final String ty;
        if (studentType.equals("PG")){
            ty = "PG";
        }
        else if (studentType.equals("PGR"))
        {
            ty = "PGR";
        }
        else
        {
            ty = "PGT";
        }
        // Move on to the next entry in the list
        studentCounter++;

        // Moduli are taken to prevent an out-of-bounds exception
        return  manager.createStudent (
                studentList.get(studentCounter%studentList.size()),
                dateList.get(studentCounter%dateList.size()),
                ty);
    }

    /* A method which returns the type-specific modules for the students
     to register to.*/
    public static String getModule (String type) {
        moduleCounter++;
        if (type.equals("PGR")) {
            return supervisorList.get(moduleCounter%supervisorList.size());
        }
        else {
            return courseModuleList.get(moduleCounter%courseModuleList.size());
        }
    }


    /* The file reader method. This method contains all the file paths which
     contain the student information required for testing.*/
    public static void prepareStudentAndDates () throws FileNotFoundException {

        // Various file paths
        File studentNames = new File("src/textfiles/StudentNames.txt");
        File studentDateOfBirths = new File("src/textfiles/StudentDOB.txt");
        File courseModules = new File("src/textfiles/CourseModules.txt");
        File courseSupervisors = new File("src/textfiles/CourseSupervisors.txt");

        // A scanner object which is used to read from the aforementioned files
        Scanner sc = new Scanner(studentNames);

        while (sc.hasNext())
        {
            final String names[] = sc.nextLine().split(" ");
            studentList.add(new Name(names[0],names[1]));
        }

        sc = new Scanner(studentDateOfBirths);
        while (sc.hasNext())
        {
            LocalDate local = LocalDate.parse(sc.nextLine());
            Date dt = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateList.add(dt);
        }

        sc = new Scanner(courseModules);
        while (sc.hasNext())
        {
            courseModuleList.add(sc.nextLine());
        }

        sc = new Scanner(courseSupervisors);
        while (sc.hasNext())
        {
            supervisorList.add(sc.nextLine());
        }
    }
}

