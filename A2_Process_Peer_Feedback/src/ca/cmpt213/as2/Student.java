package ca.cmpt213.as2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class representing student in a particular group
 *
 * @author Di Wang
 */
public class Student implements Comparable<Student> {

    private Feedback selfFeedback;

    private List<Feedback> receivedFeedbacks;

    private String name;

    private String email;

    private boolean isFeedbackSubmitted;

    private String confidential_comments;

    private double avgScore;

    Student(String name, String email) {
        this.name = name;
        this.email = email.trim();
        this.receivedFeedbacks = new ArrayList<>();
        isFeedbackSubmitted = false;
    }

    public void setIsFeedbackSubmitted(boolean feedbackSubmitted) {
        this.isFeedbackSubmitted = feedbackSubmitted;
    }

    public String getEmail() {
        return email;
    }

    public List<Feedback> getReceivedFeedbacks() {
        return receivedFeedbacks;
    }

    public boolean isFeedbackSubmitted() {
        return isFeedbackSubmitted;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Student other) {
        return email.toLowerCase()
                .compareTo(other.email.toLowerCase());
    }

    public void sort() {
        Collections.sort(receivedFeedbacks);
    }

    public String getConfidential_comments() {
        return confidential_comments;
    }

    public void setConfidential_comments(String confidential_comments) {
        this.confidential_comments = confidential_comments;
    }

    public Feedback getSelfFeedback() {
        return selfFeedback;
    }

    public void setSelfFeedback(Feedback selfFeedback) {
        this.selfFeedback = selfFeedback;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void computeAverageScore(int groupSize) {
        double totalScore = 0.0d;
        for (Feedback receiveFeedback : receivedFeedbacks) {
            totalScore += receiveFeedback.getScore();
        }
        avgScore = totalScore / (double) (groupSize - 1);
    }
}
