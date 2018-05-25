package ca.cmpt213.as5courseplanner.Model;


/**
 * Class representing data points for the graph
 * @author Banana
 */
public class GraphDataPoint {

    private int semesterCode;
    private int totalCoursesTaken;

    public GraphDataPoint(int semesterCode, int totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }
}
