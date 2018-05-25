package ca.cmpt213.as5courseplanner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Class representing course
 * @author Ermin
 */
public class Course implements Subject{

    private long courseId;
    private String catalogNumber;

    @JsonIgnore
    private List<CourseOffering> courseOfferings;

    @JsonIgnore
    private List<Observer> observers;


    public Course(long courseId, CourseData data) {
        this.courseId = courseId;
        this.catalogNumber = data.getCatalogNumber();
        this.courseOfferings = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addOffering(CourseData data) {

        int offeringFoundFlag = 0;

        for (CourseOffering eachOffering : courseOfferings) {
            if (eachOffering.getSemesterCode() == data.getSemester()) {
                offeringFoundFlag = 1;
                eachOffering.addSection(data);
                break;
            }
        }

        if (offeringFoundFlag == 0) {
            int id = courseOfferings.size() + 1;
            CourseOffering addOffering = new CourseOffering(courseId, id, data);
            addOffering.addSection(data);
            courseOfferings.add(addOffering);
            sortOfferings();
        }

        CourseOffering addedOffering = new CourseOffering(data);
        notifyObservers(addedOffering, data);
    }

    @JsonIgnore
    public CourseOffering findCourseOffering(CourseData data) {
        for (CourseOffering eachOffering : courseOfferings) {
            if (eachOffering.getSemesterCode() == data.getSemester()) {
                return eachOffering;
            }
        }

        String error = "Department " + data.getSubjectName() + ", Course " + courseId + ", Offering " + data.getSemester() + " not found.";
        throw new ResourceNotFoundException(error);
    }

    @JsonIgnore
    private void sortOfferings(){
        java.util.Collections.sort(courseOfferings, new Comparator<CourseOffering>(){
            @Override
            public int compare(CourseOffering one, CourseOffering two) {

                String one1 = "" + one.getSemesterCode() + one.getLocation();
                String two2 = "" + two.getSemesterCode() + two.getLocation();
                return one1.compareTo(two2);
            }
        });
    }


    @JsonIgnore
    public int getEnrollTotalBySemesterCode(int semesterCode) {

        int resultTotal = 0;
        for (CourseOffering eachOffering : courseOfferings) {
            if (eachOffering.getSemesterCode() == semesterCode) {
                resultTotal = eachOffering.getLectureEnrollTotal();
            }
        }

        return  resultTotal;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public long getCourseId() {
        return courseId;
    }

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(CourseOffering offering, CourseData data) {
        for (Observer eachObserver : observers) {
            eachObserver.stateChanged(offering, data);
        }
    }
}
