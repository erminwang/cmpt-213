package ca.cmpt213.as2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class which represents group for processing JSON files
 * Provide sorting and comparison functionalities
 *
 * @author Di Wang
 * @studentList Contains students in a group
 */
public class Group {

    private String groupTag;

    // an ArrayList containing all the students in the group
    private List<Student> studentList = new ArrayList<>();

    Group(int groupNumber) {
        this.groupTag = "Group " + groupNumber;
        this.studentList = new ArrayList<>();
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public boolean containsAllEmails(Set<String> emails) {
        if (emails == null || emails.isEmpty()) {
            PeerFeedbackProcessor.exitWithErrorMsg("[ERROR] There is no email in JSON file");
        }
        Set<String> emailsInGroup = new HashSet<>();
        for (Student student : studentList) {
            emailsInGroup.add(student.getEmail());
        }
        return emailsInGroup.equals(emails);
    }

    // user lambda expression to locate the student in group by email address
    public Student getStudentByEmail(String email) {
        return studentList
                .stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList())
                .get(0);
    }

    public void sort() {
        Collections.sort(studentList);
    }

    public void computeStudentAverageScore() {
        for (Student student : studentList) {
            student.computeAverageScore(studentList.size());
        }
    }
}
