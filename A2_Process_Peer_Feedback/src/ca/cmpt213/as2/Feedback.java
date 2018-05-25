package ca.cmpt213.as2;

/**
 * A class representing student review/feedback
 *
 * @author Di Wang
 */
public class Feedback implements Comparable<Feedback> {

    private double score = -1;

    private String comment;

    private String sourceStudentEmail;

    Feedback(double score, String comment, String sourceStudentEmail) {
        this.score = score;
        this.comment = comment;
        this.sourceStudentEmail = sourceStudentEmail;
    }

    @Override
    public int compareTo(Feedback other) {
        return sourceStudentEmail.toLowerCase()
                .compareTo(other.sourceStudentEmail.toLowerCase());
    }

    public double getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public String getSourceStudentEmail() {
        return sourceStudentEmail;
    }
}
