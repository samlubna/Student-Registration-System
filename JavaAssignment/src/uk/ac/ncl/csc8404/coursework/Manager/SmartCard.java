package uk.ac.ncl.csc8404.coursework.Manager;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;

/**
 * This class constitutes the SmartCard information of a registered student.
 * It contains the student's:
 * Name - A Name object
 * Date of Birth - A Date object
 * Card's Date of Issue - A Date object
 * Card's Date of Expiry - A Date object
 * Smart Card Number - A SmartCard object
 * StudentID - A StudentID object
 */
final class SmartCard {
    private final Name name;
    private final Date dateOfBirth;
    private final Date dateOfIssue;
    private final Date dateOfExpiry;
    private final SmartCardNumber number;
    private final StudentID ID;

    /**
     * The constructor for the SmartCard class
     * @param nm The name of the student: a Name object
     * @param dateOfBirth The date of birth of the student: a Date object
     * @param dateOfIssue The card's date of issue: a Date object
     * @param ID The student's ID: a StudentID object
     * @param year The duration of the card's validity: an integer
     */
    public SmartCard (Name nm, Date dateOfBirth, Date dateOfIssue,StudentID ID ,int year) {

        // Assigning the name object. No defensive copying is needed as Name is an immutable object
        this.name = nm;

        /** Assigning the date of birth. Defensive copying already happens in the method that invokes
          this constructor.
            @see StudentManager#registerStudent(Student)
         */
        this.dateOfBirth = dateOfBirth;

        // Assigning the date of issue. Defensive copying occurring
        this.dateOfIssue = new Date (dateOfIssue.getTime());

        // Assigning the date of expiry. Defensive copying occurring
        this.dateOfExpiry = setExpiryDate(new Date(dateOfIssue.getTime()),year);

        /**
         * Assigning the smart card number.
         * @see SmartCardNumber
         */
        this.number = SmartCardNumber.generateSmartCardNumber(
                nm.getFirstName(),
                nm.getLastName(),
                getYear());

        // Assigning the ID. The StudentID class is immutable.
        this.ID = ID;
    }

    /**
     * A private setter method which calculates and sets the expiration date of the card
     * @param D A date object which contains the date of issue of the the smart card
     * @param year An integer representing the duration of the card.
     * @return A Date object
     */
    private Date setExpiryDate(Date D,int year){
        return java.sql.Date.valueOf(convert(D).plusYears(year));
    }

    /**
     * A private helper method which aids the setExpiryDate in calculating the expiration date
     * @param D A Date object
     * @return A LocalDate object
     */
    private LocalDate convert (Date D){
        return D.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * A private helper function which extracts the year field from the Date of Issue object.
     * The result is then further used to generate the smart card number
     * @see SmartCardNumber
     * @return An integer: the issuing year
     */
    private int getYear (){
        return dateOfIssue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
    }

    /**
     * A getter method which returns a copy of the expiration date
     * @return A Date object
     */
    public Date getExpiryDate() {return new Date(dateOfExpiry.getTime());}

    /**
     * A getter method which returns the Name object associated with the student
     * @return A Name object
     */
    public Name getName () { return name;}

    /**
     * A getter method which returns a copy of the birth date
     * @return A Date object
     */
    public Date getBirthDate () { return new Date(dateOfBirth.getTime());}

    /**
     * A getter method which returns a copy of the issue date
     * @return A Date object
     */
    public Date getIssueDate () { return new Date(dateOfIssue.getTime());}

    /**
     * A getter method which returns a student's ID
     * @return StudentID object
     */
    public StudentID getStudentID () { return ID;}

    /**
     * The overridden toString method which provides a string representation of all the
     * student's data stored on the smart card
     * @return A String object
     */
    public String toString (){
        // Calling the class' local getter methods
        return "Name: "+getName()+"\n"+
                "Student ID: "+getStudentID().toString()+"\n"+
                "Date of Birth: "+getBirthDate()+"\n"+
                "Smart Card Number: "+number.toString()+"\n"+
                "Date of Issue: "+getIssueDate()+"\n"+
                "Date of Expiry: "+getExpiryDate();
    }
}