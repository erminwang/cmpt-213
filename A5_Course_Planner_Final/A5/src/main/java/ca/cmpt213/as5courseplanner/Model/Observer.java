package ca.cmpt213.as5courseplanner.Model;

/**
 * Interface for observers to implement to be able to observe
 */
public interface Observer {

    // The Observers update method is called when the Subject changes
    void stateChanged(CourseOffering offering, CourseData data);
}
