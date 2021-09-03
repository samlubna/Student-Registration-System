package uk.ac.ncl.csc8404.coursework.studentManagement;

import java.util.Date;

/**
 * The PGRStudent class is a student specific class that caters
 * to Post Graduate Research Students
 */
class PGRStudent extends StudentFactory {

    // The assigned supervisor name
    private Name supervisor;

    /* The credits for a PGR student are 0. The -1 initialization is done
    so that the student can be assigned a supervisor when it is amended
    for the first time */
    private int credits = -1;

    /**
     * This is the constructor for the PGRStudent class. It takes all
     * the same parameters as the StudentFactory. It calls the constructor
     * of the StudentFactory after it is invoked.
     * @param n A Name object: the student's name
     * @param DOB A Date object: the student's date of birth
     * @param type A String object: the student's program type
     */
    public PGRStudent(Name n, Date DOB, String type){
        super(n,DOB,type);
    }

    /**
     * {@link Student#listModules()}
     */
    public String listModules (){
        // Since the student is of type PGR, it checks for the supervisor.
        if (supervisor!=null) {
            // Returns the supervisor's name
            return "Supervisor: "+supervisor.toString();
        }
        else {
            return "Has not been assigned a supervisor";
        }
    }

    /**
     * The student specific moduleADD function.
     * Here it assigns a supervisor to the student
     * @param supervisorName A Name object: the student's supervisor
     * @exception NullPointerException If the Name is null
     */
    public void addSupervisor(Name supervisorName){
        // Checking for nullity
        if (supervisorName == null) {
            throw new NullPointerException(getStudentType()+" Can't be NULL: Supervisor name");
        }

        // Checking if the student has not been assigned a supervisor
            if (supervisor == null){
                supervisor = supervisorName;
                /* Increasing the credits to 0 so that the student always has
                enoughCredits*/
                credits++;
            }
        }


    /**
     * {@link Student#enoughCredits()}
     */
    public boolean enoughCredits () {
        return (credits==0);
    }
}
