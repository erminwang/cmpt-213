package ca.cmpt213.as5courseplanner.Model;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;


/**
 * Class responsible for dumping csv info to the console
 * @author Banana
 */
public class DumpModelWriter {

    public static void dumpModel(List<CourseData> courses) {

        String targetFilePath = "data/output_dump.txt";
        File fileTarget = new File(targetFilePath);
        PrintWriter printer = null;

        try {
            //printer = new PrintWriter(fileTarget);          //to generate the out_dump.txt
            printer = new PrintWriter(System.out);        //to print to console
            printToFile(printer, courses);

        }
        catch (Exception ex) {
            exitProgram("ERROR: Exception caught when dumping");
        }
        finally {
            if (printer != null) {
                printer.close();    //for outputting to a file only
            }
        }

    }

    private static void printToFile(PrintWriter printer, List<CourseData> courses) {

        final int FIRST_COURSE_INDEX = 0;

        String checkCourseName = courses.get(FIRST_COURSE_INDEX).getCourseName();
        int checkCourseSemesterId = courses.get(FIRST_COURSE_INDEX).getSemester();
        String checkCourseLocation = courses.get(FIRST_COURSE_INDEX).getLocation();

        //do iteration
        printer.println(checkCourseName);
        printer.println("\t" + courses.get(FIRST_COURSE_INDEX).getSemester() + " in " +
                courses.get(FIRST_COURSE_INDEX).getLocation() + " by " +
                courses.get(FIRST_COURSE_INDEX).getInstructor());


        for (CourseData eachCourse : courses) {

            if (eachCourse.getCourseName().equals(checkCourseName) &&
                    (eachCourse.getSemester() != checkCourseSemesterId ||
                    !eachCourse.getLocation().equals(checkCourseLocation))) {

                printer.println("\t" + eachCourse.getSemester() + " in " +
                        eachCourse.getLocation() + " by " + eachCourse.getInstructor());

                printer.println("\t\t Type=" + eachCourse.getComponent() +
                        ", Enrollment=" + eachCourse.getEnrollmentTotal() + "/" + eachCourse.getEnrollmentCap());

            }
            else if (eachCourse.getCourseName().equals(checkCourseName) &&
                    eachCourse.getSemester() == checkCourseSemesterId &&
                    eachCourse.getLocation().equals(checkCourseLocation)) {

                printer.println("\t\t Type=" + eachCourse.getComponent() +
                        ", Enrollment=" + eachCourse.getEnrollmentTotal() + "/" + eachCourse.getEnrollmentCap());

            }
            else if (!eachCourse.getCourseName().equals(checkCourseName)) {

                checkCourseName = eachCourse.getCourseName();
                printer.println(checkCourseName);
                printer.println("\t" + eachCourse.getSemester() + " in " +
                        eachCourse.getLocation() + " by " + eachCourse.getInstructor());
                printer.println("\t\t Type=" + eachCourse.getComponent() +
                        ", Enrollment=" + eachCourse.getEnrollmentTotal() + "/" + eachCourse.getEnrollmentCap());

            }

            checkCourseSemesterId = eachCourse.getSemester();
            checkCourseLocation = eachCourse.getLocation();
        }


    }

    private static void exitProgram(String errorMessage) {
        final int FAILURE = -1;
        System.out.println(errorMessage);
        System.out.println("Now exiting program.");
        System.exit(FAILURE);
    }

}
