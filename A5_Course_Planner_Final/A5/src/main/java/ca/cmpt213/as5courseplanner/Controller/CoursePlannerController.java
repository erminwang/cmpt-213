package ca.cmpt213.as5courseplanner.Controller;

import ca.cmpt213.as5courseplanner.Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * The planner controller responsible for processing incoming HTTP requests and returning correct response
 * @author Banana & Ermin
 */
@RestController
public class CoursePlannerController {

    private CourseDataExtractor courseDataExtractor = new CourseDataExtractor();
    private API myAPI = new API("Awesome Course Planner App", "Banana~");
    private List<Department> departments = courseDataExtractor.getDepartments();


    private List<Watcher> watchers = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/about")
    public API getGameAbout() {
        return myAPI;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/dump-model")
    public void dumpModel() {
        //dump to console
        courseDataExtractor.dumpModel();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments")
    public List<Department> getDepartments() {
        return departments;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{id}/courses")
    public List<Course> getOneDepartment(@PathVariable("id") long deptId) {

        for (Department eachDept : departments) {
            if (eachDept.getDeptId() == deptId) {
                return eachDept.getCourses();
            }
        }

        String error = "Department of ID " + deptId + " not found.";
        throw new DepartmentNotFoundException(error);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{id}/courses/{cid}/offerings")
    public List<CourseOffering> getCourseOffering(@PathVariable("id") long deptId,
                                                  @PathVariable("cid") long courseId) {

        int courseExceptionFlag = 0;

        for (Department eachDept : departments) {
            if (eachDept.getDeptId() == deptId) {

                courseExceptionFlag = 1;
                for (Course eachCourse : eachDept.getCourses()) {
                    if (eachCourse.getCourseId() == courseId) {
                        return eachCourse.getCourseOfferings();
                    }
                }
            }
        }

        if (courseExceptionFlag == 1) {
            String error = "Course of ID " + courseId + " not found.";
            throw new CourseNotFoundException(error);
        }

        String error = "Department of ID " + deptId + " not found.";
        throw new DepartmentNotFoundException(error);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/departments/{id}/courses/{cid}/offerings/{oid}")
    public List<Section> getCourseOfferingSection(@PathVariable("id") long deptId,
                                                  @PathVariable("cid") long courseId,
                                                  @PathVariable("oid") long offeringId) {

        int courseExceptionFlag = 0;
        int offeringExceptionFlag = 0;

        for (Department eachDept : departments) {
            if (eachDept.getDeptId() == deptId) {

                courseExceptionFlag = 1;

                for (Course eachCourse : eachDept.getCourses()) {

                    if (eachCourse.getCourseId() == courseId) {

                        offeringExceptionFlag = 1;

                        for (CourseOffering eachOffering : eachCourse.getCourseOfferings()) {

                            if (eachOffering.getCourseOfferingId() == offeringId) {
                                return eachOffering.getSections();
                            }

                        }
                    }
                }
            }
        }

        if (offeringExceptionFlag ==1) {
            String error = "Course offering of ID " + offeringId + " not found.";
            throw new OfferingNotFoundException(error);
        }
        else if (courseExceptionFlag == 1) {
            String error = "Course of ID " + courseId + " not found.";
            throw new CourseNotFoundException(error);
        }

        String error = "Department of ID " + deptId + " not found.";
        throw new DepartmentNotFoundException(error);
}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/stats/students-per-semester")
    public List<GraphDataPoint> getGraphData(@RequestParam(value="deptId") int deptId) {

        for (Department eachDept : departments) {
            if (eachDept.getDeptId() == deptId) {
                return eachDept.getDepartmentGraphData();
            }
        }

        String error = "Department of ID " + deptId + " not found.";
        throw new DepartmentNotFoundException(error);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/addoffering")
    public Section addNewOffering(@RequestBody CourseData newData) {

        courseDataExtractor.addNewData(newData, this.departments);

        for (Department eachDept : this.departments) {
            if (eachDept.getName().equals(newData.getSubjectName())) {
                return eachDept.findCourse(newData).findCourseOffering(newData).findSection(newData);
            }
        }

        String error = "New course offering failed to add.";
        throw new FailedDataAddingException(error);
    }


    //----------watcher implementation----------

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/watchers")
    public List<Watcher> getWatchers() {
        return watchers;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/watchers")
    public Watcher registerNewWatcher(@RequestBody WatcherData watcherData) {

        Department registerDept = null;
        Course registerCourse = null;

        int courseExceptionFlag = 0;

        for (Department eachDept : departments) {
            if (eachDept.getDeptId() == watcherData.getDeptId()) {
                courseExceptionFlag = 1;
                registerDept = eachDept;

                if (eachDept.findCourseById(watcherData.getCourseId()) != null) {
                    registerCourse = eachDept.findCourseById(watcherData.getCourseId());
                }
            }
        }

        if (registerCourse != null) {
            Watcher newWatcher = new Watcher(nextId.incrementAndGet(), registerDept, registerCourse);
            watchers.add(newWatcher);
            return newWatcher;
        }


        if (courseExceptionFlag == 1) {
            String error = "Course of ID " + watcherData.getCourseId() + " not found.";
            throw new CourseNotFoundException(error);
        }

        String error = "Department of ID " + watcherData.getDeptId() + " not found.";
        throw new DepartmentNotFoundException(error);
    }



    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/watchers/{id}")
    public Watcher getOneWatcher(@PathVariable("id") long watcherId) {

        for (Watcher eachWatcher : watchers) {
            if (eachWatcher.getId() == watcherId) {
                return eachWatcher;
            }
        }

        String error = "Unable to find requested watcher.";
        throw new ResourceNotFoundException(error);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/watchers/{id}")
    public void deleteWatcher(@PathVariable("id") long watcherId){

        Watcher deleteWatcher = null;

        for (Watcher eachWatcher : watchers) {
            if (eachWatcher.getId() == watcherId) {
                deleteWatcher = eachWatcher;
            }
        }

        if (deleteWatcher != null) {
            for (Department eachDept : departments) {
                if (eachDept.getDeptId() == deleteWatcher.getDepartment().getDeptId()) {
                    for (Course eachCourse : eachDept.getCourses()) {
                        if (eachCourse.getCourseId() == deleteWatcher.getCourse().getCourseId()) {
                            eachCourse.removeObserver(deleteWatcher);
                        }
                    }
                }
            }
            watchers.remove(deleteWatcher);
            return;
        }

        String error = "Unable to find requested watcher.";
        throw new ResourceNotFoundException(error);
    }



}
