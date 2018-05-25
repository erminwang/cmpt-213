package ca.cmpt213.as2;

import com.google.gson.*;

import java.io.*;
import java.util.*;

/**
 * The driver for processing .json files and convert them to .csv file
 *
 * @author Di Wang
 */
public class PeerFeedbackProcessor {

    private static final String CONFIDENTIAL_COMMENTS = "confidential_comments";
    private static final String GROUP = "group";
    private static final String SFU_EMAIL = "sfu_email";
    private static final String NAME = "name";
    private static final String CONTRIBUTION = "contribution";
    private static final String SCORE = "score";
    private static final String COMMENT = "comment";
    private static final int FAILURE = -1;

    private static List<Group> groupList = new ArrayList<>();
    private static List<File> allJsonFiles = new ArrayList<>();

    public static void main(String[] args) {

        if (args.length != 2) {
            exitWithErrorMsg("[ERROR] You did not provide correct number of arguments, " +
                    "should provide 2 arguments i.e. the paths to input folder and output folder");
        }

        File inputDir = getFolder(args[0]);
        File outputDir = getFolder(args[1]);

        listJsonFilesRecursively(inputDir);
        if (allJsonFiles.isEmpty()) {
            exitWithErrorMsg("[ERROR] No JSON file was found in the provided input folder");
        }

        for (File file : allJsonFiles) {
            try {
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(new FileReader(file));
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                JsonElement confidentialCommentsElement = jsonObject.get(CONFIDENTIAL_COMMENTS);
                checkFieldNonNull(confidentialCommentsElement, CONFIDENTIAL_COMMENTS, file);
                JsonElement groupElement = jsonObject.get(GROUP);
                checkFieldNonNull(groupElement, GROUP, file);
                if (!groupElement.isJsonArray()) {
                    exitWithErrorMsg("[ERROR] \"group\" field in file \"" + file.getName() + "\" is not a JSON array");
                } else {
                    JsonArray groupArray = groupElement.getAsJsonArray();
                    Group checkGroup = getGroup(groupArray, file);
                    addToGroupList(checkGroup, groupArray, file, confidentialCommentsElement.getAsString());
                }
            } catch (FileNotFoundException e) {
                exitWithErrorMsg("[ERROR] File \""
                        + file.getName() + "\" (Path: "
                        + file.getAbsolutePath() + ") could not be found" + e);
            } catch (JsonParseException e2) {
                exitWithErrorMsg("[ERROR] File \""
                        + file.getName() + "\" (Path: "
                        + file.getAbsolutePath() + ") is not a valid JSON file" + e2);
            }
        }
        checkGroupsValidity();
        computeAverageScores();
        generateCSVFile(outputDir);
    }

    // get folder provided by user using command line and check if the folder exists
    private static File getFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            exitWithErrorMsg("[ERROR] Path \"" + file.getAbsolutePath() + "\" doesn't exists");
        } else if (!file.isDirectory()) {
            exitWithErrorMsg("[ERROR] Path \"" + file.getAbsolutePath() + "\" is not a directory");
        }
        return file;
    }

    private static void listJsonFilesRecursively(File dir) {
        File[] jsonFiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".json");
            }
        });
        if (jsonFiles != null && jsonFiles.length > 0) {
            allJsonFiles.addAll(new ArrayList<File>(Arrays.asList(jsonFiles)));
        }
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                listJsonFilesRecursively(file);
            }
        }
    }

    /**
     * print out error message and exit the program
     *
     * @param errorMsg the error message given
     */
    public static void exitWithErrorMsg(String errorMsg) {
        System.out.println(errorMsg);
        System.exit(FAILURE);
    }

    // check if the students list in a JSON file are already created in group
    // check if the number of students in a group is different from that listed in the file
    private static Group getGroup(JsonArray array, File file) {
        Set<String> emailsInFile = new HashSet<>();
        for (JsonElement reviewElement : array) {
            JsonObject reviewObject = reviewElement.getAsJsonObject();
            JsonElement sfuEmailElement = reviewObject.get(SFU_EMAIL);
            checkFieldNonNull(sfuEmailElement, SFU_EMAIL, file);
            emailsInFile.add(sfuEmailElement.getAsString());
        }

        Group selectedGroup = null;
        for (Group group : groupList) {
            if (group.containsAllEmails(emailsInFile)) {
                selectedGroup = group;
            }
        }
        return selectedGroup;
    }

    private static void checkFieldNonNull(JsonElement field, String fieldName, File file) {
        if (field == null) {
            exitWithErrorMsg("[ERROR] A \""
                    + fieldName + "\" field in file \""
                    + file.getName() + "\" (Path: "
                    + file.getAbsolutePath() + ") is missing");
        }
    }

    /**
     * method to create new group and add it to groupList if the group doesn't exist
     * or add necessary student info to existing group
     *
     * @param group                if null, create new group; if not null, add information of the file to this group
     * @param groupArray           a JsonArray that contains information of the "group" field in JSON file
     * @param file                 the JSON file being processed
     * @param confidentialComments information of "confidential_comments" field in the JSON file
     */
    private static void addToGroupList(Group group, JsonArray groupArray, File file,
                                       String confidentialComments) {
        boolean isNewGroup = false;
        if (group == null) {
            group = new Group(groupList.size() + 1);
            isNewGroup = true;
        }
        String sourceStudentEmail = null;
        double totalScore = 0.0d;
        int groupArraySize = groupArray.size();
        for (int i = 0; i < groupArraySize; i++) {
            JsonObject reviewObject = groupArray.get(i).getAsJsonObject();
            JsonElement studentNameElement = reviewObject.get(NAME);
            checkFieldNonNull(studentNameElement, NAME, file);

            JsonElement studentEmailElement = reviewObject.get(SFU_EMAIL);
            JsonElement studentContributionElement = reviewObject.get(CONTRIBUTION);
            checkFieldNonNull(studentContributionElement, CONTRIBUTION, file);

            JsonObject studentContributionObject = studentContributionElement.getAsJsonObject();
            JsonElement feedbackScoreElement = studentContributionObject.get(SCORE);
            checkFieldNonNull(feedbackScoreElement, SCORE, file);
            JsonElement feedbackCommentElement = studentContributionObject.get(COMMENT);
            checkFieldNonNull(feedbackCommentElement, COMMENT, file);

            double score = feedbackScoreElement.getAsDouble();
            if (score < 0.0d) {
                exitWithErrorMsg("[ERROR] score provided in \'"
                        + file.getName() + "\' (Path: "
                        + file.getAbsolutePath() + ") is less than zero");
            }
            totalScore += score;
            Student student;
            if (isNewGroup) {
                student =
                        new Student(studentNameElement.getAsString(), studentEmailElement.getAsString());
            } else {
                student = group.getStudentByEmail(studentEmailElement.getAsString());
            }

            // first feedback is the student itself
            if (i == 0) {
                sourceStudentEmail = studentEmailElement.getAsString();
                student.setConfidential_comments(formatString(confidentialComments));
                student.setSelfFeedback(new Feedback(feedbackScoreElement.getAsDouble(),
                        formatString(feedbackCommentElement.getAsString()),
                        sourceStudentEmail));
                if(!student.isFeedbackSubmitted()) {
                    student.setIsFeedbackSubmitted(true);
                } else {
                    exitWithErrorMsg("[ERROR] The submitter of file \'"
                            + file.getName() + "\' (Path: "
                            + file.getAbsolutePath() + ") has submitted two feedbacks");
                }
            } else {
                student.getReceivedFeedbacks().add((new Feedback(feedbackScoreElement.getAsDouble(),
                        formatString(feedbackCommentElement.getAsString()), sourceStudentEmail)));
            }
            if (isNewGroup) {
                group.getStudentList().add(student);
            }
        }
        if (isNewGroup) {
            groupList.add(group);
        }
        if (Math.abs(totalScore - 20.0d * (double) groupArraySize) >= 0.1d) {
            exitWithErrorMsg("[ERROR] Sum of scores in file \'"
                    + file.getName() + "\' (Path: "
                    + file.getAbsolutePath() + ") is not (20 * number of group members) \n" +
                    "or the feedback provider forgot his/her groupmate(s)");
        }

    }

    /**
     * check if all students have their own feedback submitted
     * check if there are duplicate students in two different groups
     */
    private static void checkGroupsValidity() {
        Set<String> allEmails = new HashSet<>();
        for (Group group : groupList) {
            for (Student student : group.getStudentList()) {
                String email = student.getEmail().toLowerCase();
                if (!student.isFeedbackSubmitted()) {
                    exitWithErrorMsg("[ERROR] Feedback JSON file submitted by student with name \'" +
                            student.getName() + "\' and with email address \'" +
                            email + "\' is missing \n" +
                            "or this student hasn't submitted feedback\n" +
                            "or there is a typo for this email in one of the JSON files\"");
                } else if (allEmails.contains(email.toLowerCase())) {
                    exitWithErrorMsg("[ERROR] Student email \'" + email + "\' is duplicate in two different groups\n" +
                            "or other teammate forgot to mention him/her in the feedback");
                }
                allEmails.add(email);
            }
        }
    }

    private static void computeAverageScores() {
        for (Group group : groupList) {
            group.computeStudentAverageScore();
        }
    }

    private static void generateCSVFile(File dir) {
        try {

            File generatedFile = new File(dir.getAbsolutePath() + File.separator + "group_feedback.csv");
            generatedFile.createNewFile();
            PrintWriter pw = new PrintWriter(generatedFile);
            pw.println("Group#,Source Student,Target Student,Score,Comment,,Private");
            for (int i = 0; i < groupList.size(); i++) {
                Group group = groupList.get(i);
                if (i > 0) {
                    pw.println();
                }
                pw.println(group.getGroupTag());
                group.sort();
                for (Student student : group.getStudentList()) {
                    student.sort();
                    for (Feedback feedback : student.getReceivedFeedbacks()) {
                        pw.println("," + feedback.getSourceStudentEmail()
                                + "," + student.getEmail()
                                + "," + feedback.getScore()
                                + "," + feedback.getComment()
                                + ",,");
                    }
                    pw.printf(",-->"
                            + "," + student.getEmail()
                            + "," + student.getSelfFeedback().getScore()
                            + "," + student.getSelfFeedback().getComment() + "%n");

                    pw.printf(",-->"
                                    + "," + student.getEmail()
                                    + ",avg %.1f /%d"
                                    + ",,," + student.getConfidential_comments()
                                    + "%n",
                            student.getAvgScore(), group.getStudentList().size() - 1);
                }
            }
            pw.println();
            pw.close();
            System.out.println("[SUCCESS] File generated successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file for writing, file might be occupied by another program");
        } catch (IOException e2) {
            exitWithErrorMsg("[ERROR] Could not create file \'group_feedback.csv\' " + e2);
        }

    }

    private static String formatString(String str) {
        return "\"" + str.replace("\"", "\'") + "\"";
    }
}
