package uk.ac.ncl.csc8404.coursework.Manager;
import java.util.Date;

/**
 * The Student interface. It provides all the methods which
 * the client can use to interact with the student's information.
 * @author Moaz Adnan (StudentID: 200747376)
 */
public interface Student {
    /**
     * This method retrieves the student's ID
     * @exception NullPointerException if the student hasn't been
     * registered before this method is invoked.
     * @see StudentID
     * @return A StudentID object
     */
    public StudentID getStudentID();

    /**
     * This method retrieves the student's program type
     * @return A String object
     */
    public String getStudentType();

    /**
     * This method retrieves the total number of credits, and all the modules that the student is
     * currently registered to. If a student has not to registered to any modules,
     * then it returns a message informing as such.
     * @see CourseModuleClass
     * @return A String object
     */
    public String listModules();

    /**
     * This method returns the registered credits' status of a student.
     * @return A boolean
     */
    public boolean enoughCredits ();

    /**
     * This method returns the first and last names of a student in the form
     * of a Name object.
     * @see Name
     * @return A Name object
     */
    public Name returnName();

    /**
     * This method returns a Date object which contains the birth date of
     * the student. Since the Date object is mutable, a copy of the object is
     * provided instead.
     * @return A Date object
     */
    public Date returnBirthDate();

    /**
     * This method returns a Date object which contains the expiration date of
     * a student's smart card. Since the Date object is mutable,
     * a copy of the object is provided instead.
     * @return A Date object
     */
    public Date getExpiryDate();

    /**
     * This method returns the status of whether a student has been provided
     * a smart card.
     * @return A boolean
     */
    public boolean hasCard();

    /**
     * This method returns all the information related to a student. The information
     * pertains to all the data that is stored on the smart card and the course modules.
     * @see SmartCard
     * @see CourseModuleClass
     * @exception NullPointerException If the method is invoked before a student
     * has been registered.
     * @return A String object
     */
    public String getStudentInfo();
}
