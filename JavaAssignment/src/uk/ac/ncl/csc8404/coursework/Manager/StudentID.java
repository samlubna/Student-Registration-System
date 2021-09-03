package uk.ac.ncl.csc8404.coursework.Manager;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class stores the student's ID in the form four digits and a letter
 */
final class StudentID {
    // The digits component of the ID card
    private final int digits;
    // The letter component of the ID card
    private final char letter;
    /* This array stores all the letters of the alphabet, both the upper and lower case versions.
       A letter is chosen a random to be prepended to the ID */
    final static private String letterArray[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    // A Random variable of Java's Random class
    final static private Random r = new Random();
    /* The hashSet keeps track of all the IDs that were created thus far. The ID is stored as a String in the following
     format: a1234 */
    final static private Set<String> hashSet = new HashSet<>();

    /**
     * The constructor for the StudentId class
     * @param letter The letter component of the ID - a character
     * @param digits The digits component of the ID - an integer
     */
    StudentID(char letter, int digits){
        this.digits = digits;
        this.letter = letter;
    }

    /**
     * This method acts as the creation method for the StudentID. It randomly produces a character and digits
     * combination, and then check to see whether its already present. Once uniqueness is assured, it returns
     * a StudentID object
     * @return A StudentID object
     */
    public static StudentID generateID () {
        // Variables to store the two components of the student ID.
        String ID; String character; String number;
        // The while loop which continues to check if the generated ID is unique or not
        while (true) {
            // Acquiring a random letter from the letterArray
            character = letterArray[r.nextInt(letterArray.length)];
            // Producing the 4-digit number
            number = String.format("%04d", r.nextInt(9999-1000)+1000);
            // Concatenating the two components
            ID = character+number;
        // Checking if the result is unique
        if (!hashSet.contains(ID)){
            // Saving the ID
            hashSet.add(ID);
            // returning the new student ID
            return new StudentID(ID.charAt(0),Integer.parseInt(number));
        }
        }
    }

    /**
     * A getter function which retrieves the letter component
     * @return A character
     */
    public char getLetter () {return  letter;}

    /**
     * A getter function which retrieves the digits component
     * @return An integer
     */
    public int getDigits () {return  digits;}

    /**
     * The overridden toString method which provides the StudentID class'
     * display format to the output stream.
     * @return A String containing the letter and digits combination
     */
    public String toString () {
        return String.valueOf(letter)+digits;
    }

    /**
     * The overridden equals method that is used in comparison with other objects
     * @param rhs The object to be compared
     * @return A boolean based on the comparison
     */
    public boolean equals (Object rhs) {
        if (this == rhs) return true;
        if(!(rhs instanceof StudentID)) return false;
        StudentID ml = (StudentID) rhs;
        return (digits == ml.getDigits() && letter == ml.getLetter());
    }

    /**
     * The overridden hashCode function which produces an integer value to support
     * hashtable data structures. This is imperative as the StudentID is the main source
     * of uniqueness amongst registered students.
     * @return An integer value
     */
    // Taken from the lecture slides
    public int hashCode (){
        int hc = 17;
        hc = 37 * hc
                + (digits + letter);
        return 37 * hc;
    }
}
