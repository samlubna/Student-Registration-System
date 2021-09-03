package uk.ac.ncl.csc8404.coursework.Manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The PGTStudent class is a student specific class that caters
 * to Post Graduate Taught Students
 */
class PGTStudent extends StudentFactory {

    // The list holds all the modules that the student is registered to
    private final List<CourseModuleClass> moduleList = new ArrayList<>();
    // The initial credits if the student
    private int credits = 0;

    /**
     * This is the constructor for the PGTStudent class. It takes all
     * the same parameters as the StudentFactory. It calls the constructor
     * of the StudentFactory after it is invoked.
     * @param n A Name object: the student's name
     * @param DOB A Date object: the student's date of birth
     * @param type A String object: the student's program type
     */
    public PGTStudent(Name n, Date DOB, String type){
        super(n,DOB,type);
    }

    /**
     * The student specific moduleADD function.
     * Here it assigns a module to the student
     * @param courseName A Name object: the course information
     * @exception NullPointerException If the CourseModuleClass is null
     */
    public void moduleADD (CourseModuleClass courseName) {
        // Checking for nullity
        if (courseName == null) {
            throw new NullPointerException("Module required for the "+getStudentType()+" student");
        }
        // Adding the module to the student
        moduleList.add(courseName);
        // Incrementing the students credits
        credits +=  courseName.getCredits();
    }

    /**
     * {@link Student#listModules()}
     */
    public String listModules () {
        if (moduleList.size()==0){
            return "Has not registered for any modules";
        }
        // Declaring the String object to hold the module list
        String st = new String();
        // Iterating over the module list and adding it to the String
        for (int i = 0 ; i < moduleList.size(); i++){
            // Adding the string representation of the module to the string
            st = st.concat(moduleList.get(i).toString());
            // Adding a line break
            st =st.concat("\n");
        }
        // Appending the credit information at th end
        return st+"Total credits: "+credits;
    }

    /**
     * {@link Student#enoughCredits()}
     */
    public boolean enoughCredits () {
        return (credits>=180);
    }

}
