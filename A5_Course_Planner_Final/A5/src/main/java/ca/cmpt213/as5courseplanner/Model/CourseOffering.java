package ca.cmpt213.as5courseplanner.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class representing course offering
 * @author Banana
 */
public class CourseOffering {

    private long courseOfferingId;
    private OfferingCourse course;
    private String location;
    private String instructors;
    private String term;
    private int semesterCode;
    private int year;

    @JsonIgnore
    private List<Section> sections;



    public CourseOffering(long courseId, long courseOfferingId, CourseData data) {
        this.courseOfferingId = courseOfferingId;
        this.course = new OfferingCourse(courseId, data.getCatalogNumber());
        this.location = data.getLocation();
        this.instructors = data.getInstructor();
        this.term = calcTerm(data.getSemester());
        this.semesterCode = data.getSemester();
        this.year = calcYear(data.getSemester());
        this.sections = new ArrayList<>();
    }

    //for observer only
    public CourseOffering(CourseData data) {
        this.courseOfferingId = -1;
        this.course = new OfferingCourse(-1, data.getCatalogNumber());
        this.location = data.getLocation();
        this.instructors = data.getInstructor();
        this.term = calcTerm(data.getSemester());
        this.semesterCode = data.getSemester();
        this.year = calcYear(data.getSemester());
        this.sections = new ArrayList<>();
    }

    public void addSection(CourseData data){

        int sectionFoundFlag = 0;

        for (Section eachSection : sections) {
            if (eachSection.getType().equals(data.getComponent())
                    && eachSection.getEnrollmentCap() == data.getEnrollmentCap()
                    && eachSection.getEnrollmentTotal() == data.getEnrollmentTotal()) {
                sectionFoundFlag = 1;
                break;
            }
        }

        if (sectionFoundFlag == 0) {
            Section addSection = new Section(data);
            sections.add(addSection);
            sortSections();
        }
    }

    private String calcTerm(int semesterId) {

        String sem = "" + semesterId;
        int lastDigit = Integer.parseInt("" + sem.charAt(3));
        if (lastDigit == 1) {
            return "Spring";
        }
        else if (lastDigit == 4) {
            return "Summer";
        }
        else if (lastDigit == 7) {
            return "Fall";
        }
        else {
            return "Unknown";
        }

    }

    private int calcYear(int semesterId) {
        String sem = "" + semesterId;
        int firstDigit = Integer.parseInt(""+sem.charAt(0));
        int middleDigit = Integer.parseInt("" + sem.charAt(1) + sem.charAt(2));
        int year = 1900 + (100 * firstDigit) + middleDigit;
        return year;
    }

    @JsonIgnore
    public int getLectureEnrollTotal() {

        int totalResult = 0;
        for (Section eachSection : sections) {
            if (eachSection.getType().equals("LEC")) {
                totalResult = totalResult + eachSection.getEnrollmentTotal();
            }
        }

        return totalResult;
    }

    @JsonIgnore
    public Section findSection(CourseData data) {
        for (Section eachSection : sections) {
            if (eachSection.getType().equals(data.getComponent())
                    && eachSection.getEnrollmentCap() == data.getEnrollmentCap()
                    && eachSection.getEnrollmentTotal() == data.getEnrollmentTotal()) {
                return eachSection;
            }
        }

        String error = "Department " + data.getSubjectName() + ", Course " + data.getCatalogNumber() + ", Offering " + data.getSemester() +
                ", Section " + data.getComponent() + ", " + data.getEnrollmentTotal() + "/" + data.getEnrollmentCap() + " not found.";
        throw new ResourceNotFoundException(error);
    }

    @JsonIgnore
    private void sortSections(){
        java.util.Collections.sort(sections, new Comparator<Section>(){
            @Override
            public int compare(Section one, Section two) {
                return one.getType().compareTo(two.getType());
            }
        });
    }

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public OfferingCourse getCourse() {
        return course;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructors() {
        return instructors;
    }

    public String getTerm() {
        return term;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public int getYear() {
        return year;
    }

    public List<Section> getSections() {
        return sections;
    }
}
