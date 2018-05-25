package ca.cmpt213.as5courseplanner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Class representing department
 * @author Ermin
 */
public class Department {

    private long deptId;
    private String name;

    @JsonIgnore
    private List<Course> courses;               //this is the course list we need to implement observer on

    @JsonIgnore
    private List<Integer> semesterCodes;


    public Department(long deptId, CourseData data) {
        this.deptId = deptId;
        this.name = data.getSubjectName();
        this.courses = new ArrayList<>();
        this.semesterCodes = new ArrayList<>();
    }

    public void addCourse(CourseData data) {
        //find id, if course exists, add offering else add course using course data

        int courseFoundFlag = 0;
        int semesterCodeExistFlag = 0;

        name.getClass

        for (Integer code : semesterCodes) {
            if (code == data.getSemester()) {
                semesterCodeExistFlag = 1;
                break;
            }
        }

        if (semesterCodeExistFlag == 0) {
            semesterCodes.add(data.getSemester());
        }

        for (Course eachCourse : courses) {
            if (eachCourse.getCatalogNumber().equals(data.getCatalogNumber())) {
                courseFoundFlag = 1;
                eachCourse.addOffering(data);
                break;
            }
        }

        if (courseFoundFlag == 0) {
            int id = courses.size() + 1;
            Course addCourse = new Course(id, data);
            addCourse.addOffering(data);
            courses.add(addCourse);
            sortCourses();
        }

    }


    @JsonIgnore
    public void sortCourses(){
        java.util.Collections.sort(courses, new Comparator<Course>(){
            @Override
            public int compare(Course one, Course two) {
                return one.getCatalogNumber().compareTo(two.getCatalogNumber());
            }
        });
    }

    @JsonIgnore
    public Course findCourse(CourseData data) {


        for (Course eachCourse : courses) {
            if (eachCourse.getCatalogNumber().equals(data.getCatalogNumber())) {
                return eachCourse;
            }
        }

        String error = "Department " + deptId + ", Course " + data.getCatalogNumber() +  " not found.";
        throw new ResourceNotFoundException(error);

    }

    @JsonIgnore
    public Course findCourseById(long cid) {
      for (Course eachCourse : courses) {
            if (eachCourse.getCourseId() == cid) {
                return eachCourse;
            }
      }

        String error = "Course of ID " + cid +  " not found.";
        throw new CourseNotFoundException(error);
    }

    public String getName() {
        return name;
    }

    public long getDeptId() {
        return deptId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @JsonIgnore
    public List<GraphDataPoint> getDepartmentGraphData() {
        List<GraphDataPoint> result = new ArrayList<>();


        for (Integer semesterCode : semesterCodes) {
            int enrollTotal = 0;
            for (Course eachCourse : courses) {
                enrollTotal = enrollTotal + eachCourse.getEnrollTotalBySemesterCode(semesterCode);
            }

            GraphDataPoint addPoint = new GraphDataPoint(semesterCode, enrollTotal);
            result.add(addPoint);
        }

        java.util.Collections.sort(result, new Comparator<GraphDataPoint>(){
            @Override
            public int compare(GraphDataPoint p1, GraphDataPoint p2) {
                return p1.getSemesterCode() - p2.getSemesterCode();
            }
        });

        return result;
    }




}
