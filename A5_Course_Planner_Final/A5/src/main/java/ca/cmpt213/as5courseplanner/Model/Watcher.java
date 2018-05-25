package ca.cmpt213.as5courseplanner.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Course observer class to watch activities on courses
 * @author Banana
 */
public class Watcher implements Observer{

    private long id;
    private Department department;
    private Course course;
    private List<String> events;

    public Watcher(long id, Department department, Course course) {
        this.id = id;
        this.department = department;
        this.course = course;
        this.events = new ArrayList<>();


        course.addObserver(this);

    }

    @Override
    public void stateChanged(CourseOffering offering, CourseData data) {
        Date now = new Date();
        String update = "" + now + ": Added section " + data.getComponent() + " with enrollment (" +
                data.getEnrollmentTotal() + "/" + data.getEnrollmentCap() + ") to offering " +
                offering.getTerm() + " " + offering.getYear();
        events.add(update);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public void addEvents(String newEvent) {
        this.events.add(newEvent);
    }


}
