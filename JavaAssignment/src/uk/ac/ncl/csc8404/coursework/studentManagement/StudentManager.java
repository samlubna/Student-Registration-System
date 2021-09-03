package uk.ac.ncl.csc8404.coursework.studentManagement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The StudentManager class acts as the mediator between the university's system
 * and the students. Any interaction that the university decides to have with the
 * students goes though this class. This class only has one instance which can manage
 * the students, i.e., a singleton approach
 */
public class StudentManager {

    /* The studentRecord variable acts as the 'registration system' which
     keeps track of the registered students. It uses the student's ID to conclude
     whether the student is registered or not. */
    final private static Map<StudentID, Student> studentRecord = new HashMap<>();

    // Counters for the different types of students
    private static int UGStudent = 0;
    private static int PGTStudent = 0;
    private static int PGRStudent = 0;

    /* The StudentManger variable that is used by the university's system to deal
     with the students */
    final private static StudentManager manager = new StudentManager();

    /**
     * The constructor for the StudentManager class.
     */
    private StudentManager () {};

    /**
     * The getInstance method returns the local StudentManager variable as the
     * only instance of this class
     * @return A StudentManager object
     */
    public static StudentManager getInstance(){
        return manager;
    }

    /**
     * This method invokes the StudentFactory's getInstance method to dynamically
     * return a student based on the parameters provided.
     * @see StudentFactory#getInstance(Name, Date, String)
     * @param name The student's name: A Name object
     * @param dateOfBirth The student's date of birth: A Date object
     * @param type The student's program type: A String
     * @return A Student object
     */
    public  Student createStudent (Name name, Date dateOfBirth, String type){
        return StudentFactory.getInstance(name,dateOfBirth,type);
    }

    /**
     * This method registers the student with the university. The registration
     * process is simply assigning the student a smart card, with all the information
     * that it entails, and noting a new entry in the studentRecord.
     * @exception NullPointerException If the Student object is null
     * @exception IllegalArgumentException If a duplicate registration is attempted,
     * or if a terminated student is being re-registered.
     * @param s A Student object
     */
    public  void registerStudent(Student s) {

        // Checking for nullity
        if (s == null) {
            throw new NullPointerException("A student needs to be provided");
        }

        /* Since there is no formal termination of a student, the termination
          is symbolised by removing that student's entry from the studentRecords.
          So if a terminated student tries to re-register,
          it would still have card as the students aren't 'deleted'.*/
        else if (s.hasCard() && !studentRecord.containsKey(s.getStudentID())) {
            throw new IllegalArgumentException("attempt at re-registering a terminated student");
        }

        // If a student is both active and registered, then the following statement would indicate duplication.
        else if (s.hasCard()) {
            throw new IllegalArgumentException("attempt at duplicate registration of " +
                    "Student with name: " + s.returnName().toString());
        }

        // Assign a smart card if the student is unregistered and doesn't have a card
        else {
            // Creating the issue date from the current time
            Date issue = new Date();

            // Generating the studentID
            StudentID sd = StudentID.generateID();

            // Checking for the student types
            if (s.getStudentType().equals("UG")) {
                // Creating the smart card for a UG student
                SmartCard sp = new SmartCard(s.returnName(), s.returnBirthDate(),issue, sd,4);
                /* Casting down to the UGStudent. Instance of isn't checked as down-casting to a
                subclass is always safe. Student - > UGStudent */
                UGStudent ugStudent = (UGStudent)s;
                // Invoking the student-specific assign method to assign the smart card
                ugStudent.assignCard(sp);
                // Incrementing the UGStudent counter to indicate a new entry
                UGStudent++;
            }
            else if (s.getStudentType().equals("PGT")) {
                // Creating the smart card for a PGT student
                SmartCard sp = new SmartCard(s.returnName(), s.returnBirthDate(), issue, sd,2);
                 /* Casting down to the PGTStudent. Instance of isn't checked as down-casting to a
                subclass is always safe. Student - > PGTStudent */
                PGTStudent pgtStudent = (PGTStudent)s;
                // Invoking the student-specific assign method to assign the smart card
                pgtStudent.assignCard(sp);
                // Incrementing the PGTStudent counter to indicate a new entry
                PGTStudent++;
            }
            else if (s.getStudentType().equals("PGR")) {
                // Creating the smart card for a PGR student
                SmartCard sp = new SmartCard(s.returnName(), s.returnBirthDate(), issue, sd,5);
                 /* Casting down to the PGRStudent. Instance of isn't checked as down-casting to a
                subclass is always safe. Student - > PGRStudent */
                PGRStudent pgrStudent = (PGRStudent)s;
                // Invoking the student-specific assign method to assign the smart card
                pgrStudent.assignCard(sp);
                // Incrementing the PGRStudent counter to indicate a new entry
                PGRStudent++;
            }

            // Recording the entry
            studentRecord.put(sd, s);
        }
    }

    /**
     * This method returns the number of students that are currently registered with the university.
     * type provided
     * @param type The program type of the students: A String
     * @exception NullPointerException If a null type is provided
     * @exception IllegalArgumentException If an undefined type is provided
     * @return An integer
     */
    public int noOfStudents (String type) {
        //Checking for nullity
        if (type == null) {
            throw new NullPointerException("requires a String object");
        }
        // Checking for "UG"
        if (type.equals("UG")) return UGStudent;

        // Checking for "PGT"
        else if (type.equals("PGT")) return PGTStudent;

        // Checking for "PGR"
        else if (type.equals("PGR")) return PGRStudent;

        throw new IllegalArgumentException("undefined type provided");
    }

    /**
     * This method terminates the student by removing its studentID entry from the
     * studentRecords. The student object isn't terminated though. All the methods available in the
     * Student interface will still work as intended, it's just that the student is no longer recognized
     * by the university. Since the student's uniqueness is tracked by storing key-value
     * pairs in the student factory, there will always be a copy of the generated student
     * even after it has been 'terminated' by the student manager. This function does not 
     * provide a clean-up process for any replication that might have happened in the other
     * classes
     * @see StudentFactory#getInstance(Name, Date, String)
     * @see Student
     * @param studentID A studentID object
     * @exception NullPointerException If the StudentID object is null
     * @exception IllegalArgumentException If the provided student ID is invalid
     */
    public void terminateStudent (StudentID studentID) {

        // Checking for nullity
        if (studentID == null){
            throw new NullPointerException("requires a StudentID");
        }

        // Checking for validity
        if (studentRecord.containsKey(studentID)){
            // Removing the student from the studentRecords and returning the respective student object
            Student s = studentRecord.remove(studentID);
            // Decrementing the current student type count
            // UGStudent reduces by one
            if (s.getStudentType().equals("UG")) {UGStudent--;}

            // PGRStudent reduces by one
            else if(s.getStudentType().equals("PGR")) {PGRStudent--;}

            // PGTStudent reduces by one
            else PGTStudent--;
        }
        else {
            throw new IllegalArgumentException("The provided studentID is invalid");
        }
    }

    /**
     * This method updates a student's record by registering him/her for a course. Since not
     * all of the students register for modules, the method further checks the student's type
     * to see if the format of the student data is valid.
     * @param Id The student's ID: A StudentID object
     * @param studentData A String containing the information of the course.
     * @exception NullPointerException If either input is null, or if the student's ID is invalid
     * @exception IllegalArgumentException If the format of the student data is incorrect for the student's type
     */
    public void amendStudentData(StudentID Id, String studentData) {

        // Checking for nullity
        if (Id == null || studentData == null) {
            throw new NullPointerException("Both the student's ID and data are required");
        }

        // Checking for validity
        if(!studentRecord.containsKey(Id)){
            throw new NullPointerException("Invalid ID");
        }

        // Retrieving the student from the studentRecords
        Student s = studentRecord.get(Id);

        /*Checking to see if the student has enough credits. Only amending the student's record if
         the student requires more credit hours*/
        if (s.enoughCredits()) {
            System.out.println("The student is registered properly to the "
                    +s.getStudentType()+" program");
        }

        /* Deciphering the type of student. Since only two types of students register for modules, down-casting
         is done to invoke the student-specific module assignment*/
        else {

            if (s.getStudentType().equals("PGT"))
            {       // Down-casting the student object
                    PGTStudent sp = (PGTStudent)s;
                    // Calling the student-specific moduleADD method
                    sp.moduleADD(new CourseModuleClass(studentData));
                }

            else if (s.getStudentType().equals("UG"))
            {
                    // Down-casting the student object
                    UGStudent sp = (UGStudent) s;
                    // Calling the student-specific moduleADD method
                    sp.moduleADD(new CourseModuleClass(studentData));

                }
            else if (s.getStudentType().equals("PGR"))
            {
                // Down-casting the student object
                PGRStudent sp = (PGRStudent) s;

                /* The PGR student can only be assigned one supervisor, hence its studentData format is
                 different from the other two */
                // Splitting the provided data.
                final String n[] = studentData.split(" ");
                // Checking for validity
                if (n.length!=2){
                    throw new IllegalArgumentException("Incorrect studentData input for student type PGR:" +
                            " "+studentData);
                }
                // Calling the student-specific moduleADD method
                sp.addSupervisor(new Name(n[0],n[1]));
            }
        }

    }
}
