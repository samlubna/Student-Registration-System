package uk.ac.ncl.csc8404.coursework.studentManagement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class generates and stores the smart card number of the smart card
 * issued to the student. It is comprised of three parts:
 * The student's initial - A String object,
 * The card's year of issue -  An integer,
 * A random number -  An integer
 */
final class SmartCardNumber{

    // A random variable of Java's Random class
    final static private Random r = new Random();

    /* This variable keeps track of all the generated smart card numbers in the following string format: lM-2019-67*/
    final static private Set<String> hashSet = new HashSet<>();

    // The initials of the student
    final private String initials;

    /** The year of issue extracted form the Date object containing the date of issue
     * @see SmartCard
     */
    final private int dateOfIssueYear;

    // The random number to be appended
    final private int number;

    /**
     * The constructor for the SmartCardNumber class
     * @param initials A string object possessing the initials of the student's name
     * @param dateOfIssueYear An integer representing the year of issue
     * @param number A random integer forming the third part of the smart card number
     */
    SmartCardNumber(String initials, int dateOfIssueYear, int number ) {
        this.initials = initials;
        this.dateOfIssueYear = dateOfIssueYear;
        this.number = number;
    }

    /**
     * This method generates the smart card number. It randomly generates the last digits of the smart card
     * number, concatenates the three parts, and checks for uniqueness.
     * @param fName The first name of the student: a String object
     * @param lName The last name of the student: a String object
     * @param dateOfIssueYear The year of issue: an integer
     * @return A SmartCardNumber object
     */
    public static SmartCardNumber generateSmartCardNumber(String fName, String lName, int dateOfIssueYear)
    {
        // Obtaining the initials of the student's name
        final String studentInitials =
                String.valueOf(fName.charAt(0)).toUpperCase()+
                String.valueOf(lName.charAt(0)).toUpperCase();
        // Checking for exclusivity for the generated number
        while (true)
        {
        // Producing a random number and appending the result to the initials and the year of issue
        final String lk =
                studentInitials+'-'
                +dateOfIssueYear+'-'
                +String.format("%02d", r.nextInt(99));
        // Checking if the result is unique
        if (!hashSet.contains(lk))
        {
            // Splitting the the exclusive smart card number and passing the constituents to its constructor
            final String [] parts = lk.split("-");
            return new SmartCardNumber(studentInitials,
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]));
        }
        }
    }

    /**
     * The overridden toString function which returns a custom string representation
     * of the smart card number
     * @return A String object
     */
    public String toString()
    {
        return this.initials+'-'+this.dateOfIssueYear+'-'+this.number;
    }
}
