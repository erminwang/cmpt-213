package ca.cmpt213.as5courseplanner.Model;


/**
 * Interface for subjects to implement to be able to be observed
 */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(CourseOffering offering, CourseData data);
}

