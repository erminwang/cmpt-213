package ca.cmpt213.as5courseplanner.Model;


/**
 * Class that hold incoming HTTP request data to create watchers
 * @author Banana
 */
public class WatcherData {

    private long deptId;
    private long courseId;

    public WatcherData() {
    }

    public WatcherData(long deptId, long courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public long getDeptId() {
        return deptId;
    }

    public long getCourseId() {
        return courseId;
    }
}
