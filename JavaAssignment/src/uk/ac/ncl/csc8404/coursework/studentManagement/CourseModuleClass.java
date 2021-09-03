package uk.ac.ncl.csc8404.coursework.studentManagement;

/**
 * This class stores the modules of the students who have registered
 * for programs that support modules. It is comprised of three things:
 * The module code - A String object
 * The module name - A String object
 * The number of credits - An integer
 */
final class CourseModuleClass {
    private final String name;
    private final String moduleCode;
    private final int credits;

    /**
     * The constructor for the CourseModuleClass
     * @param studentData A String containing the student's module information
     * @exception NullPointerException If there is no module data provided
     * @exception IllegalArgumentException If the format of the date is invalid
     */
    public CourseModuleClass(String studentData){
        // Checking for nullity
        if(studentData == null){
            throw new NullPointerException("The course module is required");
        }
        // Splitting the string
        final String [] parts = studentData.split(",");
        // Checking if the string meets the required format for the CourseModuleClass
        if (parts.length != 3) {
            throw new IllegalArgumentException("Incorrect student input for student type: PGT/UG" +
                    ": "+studentData);
        }
        // Assigning the different components
        this.moduleCode = parts[0].trim();
        this.name = parts[1].trim();
        this.credits = Integer.parseInt(parts[2].trim());
    }

    /**
     * The overridden toString method which provides a string format for the CourseModuleClass
     * @return A String object
     */
    public String toString (){
        return this.moduleCode+", "+this.name+", "+this.credits;
    }

    /**
     * A getter method which returns the number of credits associated with the program
     * @return An integer
     */
    public int getCredits () { return credits;}
}
