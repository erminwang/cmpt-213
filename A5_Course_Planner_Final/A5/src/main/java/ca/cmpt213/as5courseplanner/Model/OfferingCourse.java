package ca.cmpt213.as5courseplanner.Model;


/**
 * Class representing course in the course offering class
 * @author Banana
 */
public class OfferingCourse {

    private long courseId;
    private String catalogNumber;

    public OfferingCourse(long courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }
}
