package ca.cmpt213.as5courseplanner.Model;


/**
 * Class representing section
 * @author Banana
 */
public class Section {

    private String type;
    private int enrollmentCap;
    private int enrollmentTotal;


    public Section(CourseData data) {
        this.type = data.getComponent();
        this.enrollmentCap = data.getEnrollmentCap();
        this.enrollmentTotal = data.getEnrollmentTotal();
    }

    public String getType() {
        return type;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }
}
