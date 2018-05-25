package ca.cmpt213.as5courseplanner.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Class responsible to extract data from csv and incoming HTTP requests
 * @author Banana
 */
public class CourseDataExtractor {

    private List<CourseData> courses;
    private List<Department> departments = new ArrayList<>();

    public CourseDataExtractor() {
        this.courses = new ArrayList<>();
        extractCourseData();
    }

    private void extractCourseData() {

        final int SEMESTER_INDEX = 0;
        final int SUBJECT_INDEX = 1;
        final int CAT_INDEX = 2;
        final int LOCATION_INDEX = 3;
        final int ENROLLCAP_INDEX = 4;
        final int ENROLLTOTAL_INDEX = 5;
        final int INSTRUCTOR_INDEX = 6;
        final int COMPONENTCODE_INDEX = 7;

        //File csvCoursesFile = new File("data/original.csv");                      //smaller test file
        File csvCoursesFile = new File("data/course_data_2018.csv");      //given csv file
        Scanner scanner = null;

        try{
            scanner = new Scanner(csvCoursesFile);

            //skip title
            scanner.nextLine();

            String currentLine;
            while(scanner.hasNext()){

                currentLine = scanner.nextLine();
                List<String> courseInfo;

                boolean oneInstructorOnly = !currentLine.contains("\"");

                courseInfo = new ArrayList<String>(Arrays.asList(currentLine.split(",")));

                if (!oneInstructorOnly) {

                    List<String> temp = new ArrayList<>();

                    int componentCodeIndex = courseInfo.size() - 1;

                    String instructorNames = "";
                    for (int i = 6; i < componentCodeIndex; i++) {

                        String currentInstructorName = courseInfo.get(i).replaceAll("\"", "");
                        instructorNames += currentInstructorName + ", ";
                        //System.out.println(instructorNames);
                    }

                    //correcting the format
                    instructorNames = instructorNames.replaceAll("\\s+,\\s$", "");
                    temp.add(courseInfo.get(SEMESTER_INDEX));
                    temp.add(courseInfo.get(SUBJECT_INDEX));
                    temp.add(courseInfo.get(CAT_INDEX));
                    temp.add(courseInfo.get(LOCATION_INDEX));
                    temp.add(courseInfo.get(ENROLLCAP_INDEX));
                    temp.add(courseInfo.get(ENROLLTOTAL_INDEX));
                    temp.add(instructorNames);
                    temp.add(courseInfo.get(componentCodeIndex));
                    courseInfo = temp;

                }

                //testing
//                for (String eachString : courseInfo) {
//                    System.out.println(eachString);
//                }
//                System.out.println(courseInfo.size());

                addNewCourse(courseInfo.get(SEMESTER_INDEX).replaceAll("\\s+$",""),
                        courseInfo.get(SUBJECT_INDEX).replaceAll("\\s+$",""),
                        courseInfo.get(CAT_INDEX).replaceAll("\\s+$",""),
                        courseInfo.get(LOCATION_INDEX).replaceAll("\\s+$",""),
                        courseInfo.get(ENROLLCAP_INDEX).replaceAll("\\s+$",""),
                        courseInfo.get(ENROLLTOTAL_INDEX).replaceAll("\\s+$",""),
                        courseInfo.get(INSTRUCTOR_INDEX).replaceAll("\\s+$",""),
                        courseInfo.get(COMPONENTCODE_INDEX).replaceAll("\\s+$","")
                        );


            }

        }
        catch(FileNotFoundException ex) {
            exitProgram("Input data file not found.");
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        sortCourse();

        departmentsInitializer();



    }

    private void addNewCourse(String semesterId, String subject,
                              String catalogNumber, String location,
                              String enrollmentCapacity, String enrollmentTotal,
                              String instructor, String componentCode){

        int alreadyExist = 0;
        String newCourseName = subject + " " + catalogNumber;
        String aggregatedInstructor = "";
        int aggregatedInstructorFlag = 0;

        for (CourseData eachCourse : courses) {

            if (eachCourse.getCourseName().equals(newCourseName) &&
                    eachCourse.getSemester() == Integer.parseInt(semesterId) &&
                    eachCourse.getComponent().equals(componentCode) &&
                    eachCourse.getLocation().equals(location)) {

                alreadyExist = 1;

                eachCourse.increaseEnrollmentCapacity(Integer.parseInt(enrollmentCapacity));
                eachCourse.increaseEnrollmentTotal(Integer.parseInt(enrollmentTotal));

                if (!eachCourse.getInstructor().contains(instructor)) {
                    eachCourse.addInstructor(instructor);
                }

                break;

            }

            if (eachCourse.getCourseName().equals(newCourseName) &&
                    eachCourse.getSemester() == Integer.parseInt(semesterId) &&
                    eachCourse.getLocation().equals(location)) {

                if (!eachCourse.getInstructor().contains(instructor)) {
                    eachCourse.addInstructor(instructor);
                    aggregatedInstructor = eachCourse.getInstructor();
                    aggregatedInstructorFlag = 1;
                }

            }


        }

        if (alreadyExist == 0 && aggregatedInstructorFlag == 1) {


            courses.add(new CourseData(Integer.parseInt(semesterId),
                    subject,
                    catalogNumber,
                    location,
                    Integer.parseInt(enrollmentCapacity),
                    Integer.parseInt(enrollmentTotal),
                    aggregatedInstructor,
                    componentCode));

            aggregatedInstructorFlag = 0;

        }
        else if (alreadyExist == 0) {
            courses.add(new CourseData(Integer.parseInt(semesterId),
                    subject,
                    catalogNumber,
                    location,
                    Integer.parseInt(enrollmentCapacity),
                    Integer.parseInt(enrollmentTotal),
                    instructor,
                    componentCode));
        }


    }

    //fixed bug: remember system.out should not be close().
    public void dumpModel() {

        DumpModelWriter.dumpModel(this.courses);

    }

    private void sortCourse(){
        java.util.Collections.sort(courses, new Comparator<CourseData>(){
            @Override
            public int compare(CourseData course1, CourseData course2) {

                String course1Info = course1.getCourseName() + course1.getSemester() +
                        course1.getLocation() + course1.getComponent();

                String course2Info = course2.getCourseName() + course2.getSemester() +
                        course2.getLocation() + course2.getComponent();

                return course1Info.compareTo(course2Info);
            }
        });
    }

    public List<CourseData> getCourses() {
        return this.courses;
    }

    public List<Department> getDepartments() {
        return this.departments;
    }

    private void departmentsInitializer() {

        int deptFoundFlag = 0;

        for (CourseData data : courses) {

            for (Department eachDepartment : departments) {
                //if match, add course
                //new department is identified by subject in this case
                if (eachDepartment.getName().equals(data.getSubjectName())) {
                    deptFoundFlag = 1;
                    eachDepartment.addCourse(data);
                    break;
                }
            }

            if (deptFoundFlag == 0) {
                //add department then add course, i++
                int id = departments.size() + 1;
                Department addDepartment = new Department(id, data);
                addDepartment.addCourse(data);
                departments.add(addDepartment);
            }

            //reset
            deptFoundFlag = 0;

        }

    }

    public void addNewData(CourseData data, List<Department> myDepts) {

        int deptFoundFlag = 0;

        if (data == null || myDepts == null) {
            exitProgram("Null value passed in");
        }

        for (Department eachDepartment : myDepts) {
            //if match, add course
            //new department is identified by subject in this case
            if (eachDepartment.getName().equals(data.getSubjectName())) {
                deptFoundFlag = 1;
                eachDepartment.addCourse(data);
                break;
            }
        }

        if (deptFoundFlag == 0 && data != null) {
            //add department then add course, i++
            int id = myDepts.size() + 1;
            Department addDepartment = new Department(id, data);
            addDepartment.addCourse(data);
            myDepts.add(addDepartment);
            sortDepartment(myDepts);
        }

        courses.add(data);
        sortCourse();

    }

    private void sortDepartment(List<Department> depts){
        java.util.Collections.sort(depts, new Comparator<Department>(){
            @Override
            public int compare(Department one, Department two) {
                return one.getName().compareTo(two.getName());
            }
        });
    }

    private void exitProgram(String errorMessage) {
        final int FAILURE = -1;
        System.out.println(errorMessage);
        System.out.println("Now exiting program.");
        System.exit(FAILURE);
    }

}
