package uk.ac.ncl.csc8404.coursework.studentManagement;

/**
 * This class stores the first and last names of the students.
 */
public final class Name {
    final private String firstName;
    final private String lastName;

    /**
     * The constructor for the Name class
     * @param fName The first name of the student: a String
     * @param lName The second name of the student: a String
     * @exception NullPointerException If either parameter is null
     * @exception IllegalArgumentException If either parameter is empty
     */
    public Name(String fName, String lName) {
        if (fName == null || lName == null) {
            throw new NullPointerException("The first and last names are required for the name class");
        }
        if (fName.trim() == "" || lName.trim() == ""){
            throw new IllegalArgumentException("Empty names are not allowed");
        }
        this.firstName = fName.trim();
        this.lastName = lName.trim();
    }

    /**
     * A getter method for the Name class. It returns the first name
     * @return The first name: a String
     */
    public String getFirstName() { return firstName;}

    /**
     * A getter method for the Name class. It returns the last name
     * @return The last name: a String
     */
    public String getLastName() { return lastName;}

    /**
     * The overridden toString method which provides the Name class'
     * display format to the output stream.
     * @return A String containing the first and the last names
     */
    public String toString () {
        return firstName+" "+lastName;
    }



}
