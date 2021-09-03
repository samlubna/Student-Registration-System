package uk.ac.ncl.csc8404.coursework.Manager;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This abstract class implements the common methods amongst all the student types.
 * It acts as the selection and production point for all the Student Objects.
 */
abstract class StudentFactory implements Student {
    // Common data members amongst all students.
    private final Name name;
    private final String Type;
    private final Date dateOfBirth;
    private  SmartCard Sm;
    /*A static data member which keeps track of all the student objects that have been created.
      It ensures uniqueness by storing the names, and the Student objects in a key-value pair.*/
    private static final Map<String,Student> studentNames = new HashMap<>();

    /**
     * The constructor for the StudentFactory Class.
     * @see Name
     * @param nm A Name object: represents the first and last names of the student
     * @param dateOfBirth A Date object: represents the date of birth of the student
     * @param type A String object: represents the program type of the student
     */
    StudentFactory (Name nm, Date dateOfBirth, String type) {
        this.name = nm;
        this.dateOfBirth = dateOfBirth;
        this.Type = type;
    }

    /**
     * This method acts as the factory method for the student class. Based on the three parameters, It can
     * return one of the following Student types: PGRStudent, PGTStudent, or a UGStudent
     * @param nm A Name object: represents the first and last names of the student
     * @param DOB A Date object: represents the date of birth of the student
     * @param type A String object: represents the program type of the student
     * @exception NullPointerException If any of the parameters are null
     * @exception IllegalArgumentException If either the type parameter is incorrect, or the age of the student
     * is below the age of application for the course.
     * @return A Student object
     */
    public static Student getInstance (Name nm, Date DOB, String type) {
        // Checking for nullity
        if (nm == null || DOB == null || type == null) {
            throw new NullPointerException("The Name, Date and Type fields are mandatory");
        }
        // Defensive copying of a mutable object
        Date db = new Date(DOB.getTime());
        // Ensuring a standardized format for the key: James == JAmes == jaMes
        final String key = nm.toString().toLowerCase();
        // Checking to see if the name is unique
        if (studentNames.containsKey(key)) {
            return studentNames.get(key);
        }
        // Checking the type and age of the student to select the relevant student type
        if (type.equals("PGT")) {
            if (calAge(DOB) >= 20) {
                studentNames.put(key,new PGTStudent(nm,db,type));
                return studentNames.get(key);
            } else {
                throw new IllegalArgumentException("The student must be at least 20" +
                        " years old to register for this course "+type);
            }
        }
        else if (type.equals("PGR")){
            if (calAge(DOB) >= 20) {
                studentNames.put(key,new PGRStudent(nm,db,type));
                return studentNames.get(key);
            }else {
                throw new IllegalArgumentException("The student must be at least 20" +
                        " years old to register for this course "+type);
            }
        }
        else if (type.equals("UG")) {
            if (calAge(DOB) >= 17) {
                studentNames.put(key,new UGStudent(nm,db,type));
                return studentNames.get(key);
            } else {
                throw new IllegalArgumentException("The student must be at least 17" +
                        " years old to register for this course "+type);
            }
        }
        throw new IllegalArgumentException("Invalid Student type "+type);
    }

    /**
     * {@link Student#getStudentID()}
     */
    @Override
    public StudentID getStudentID () {
        if (hasCard()) {
            return Sm.getStudentID();
        }
        throw new NullPointerException("Student not registered: "+returnName().toString());

    }

    /**
     * {@link Student#getStudentType()}
     */
    @Override
    public String getStudentType (){
        return Type;
    }

    /**
     * {@link Student#returnName()}
     */
    @Override
    public Name returnName() {
        return name;
    }

    /**
     * {@link Student#returnBirthDate()}
     */
    @Override
    public Date returnBirthDate () { return new Date(dateOfBirth.getTime()); }

    /**
     * {@link Student#getExpiryDate()}
     */
    @Override
    public Date getExpiryDate (){
        if (hasCard()) {
            return Sm.getExpiryDate();
        }
        throw new NullPointerException("Student not registered: "+returnName().toString());
    }

    /**
     * {@link Student#hasCard()}
     */
    @Override
    public boolean hasCard() {
        return (this.Sm != null);
    }

    /**
     * {@link Student#getStudentInfo()}
     */
    @Override
    public String getStudentInfo (){
        if(hasCard()) {
            return Sm.toString()+"\n"+"Student type: "+getStudentType()+"\n"
                    +listModules();
        }
        throw new NullPointerException("Student not registered: "+returnName().toString());
    }

    /**
     * This method calculates the age of the student. It acts as a helper function for the StudentFactory.
     *  Its only parameter is a Date object. It first converts the Date object into a LocalDate object, and then uses
     *  Java's builtin Period library to ascertain the age.
     * @see Date
     * @see LocalDate
     * @see Period
     * @param A The student's age in the form of a Date object
     * @return An integer representing the age of the student
     */
    private static int calAge (Date A) {
        LocalDate birth = A.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birth,LocalDate.now()).getYears();
    }

    /**
     * This method assigns a smart card to the user.
     * @see SmartCard
     * @exception NullPointerException If a null object is passed as the SmartCard parameter.
     * @param S A SmartCard object
     */
    public void assignCard (SmartCard S){
        if (S==null){
            throw new NullPointerException("A SmartCard object is required");
        }
        if (!hasCard()){
            this.Sm = S;
        }
    }
}
