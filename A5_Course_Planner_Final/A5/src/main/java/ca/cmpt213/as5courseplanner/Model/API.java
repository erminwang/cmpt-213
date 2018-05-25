package ca.cmpt213.as5courseplanner.Model;


/**
 * Class containing author info
 * @author Ermin
 */
public class API {
    private String appName = "Awesome Course Planner App";
    private String authorName = "Banana";

    public API(String appName, String authorName) {
        this.appName = appName;
        this.authorName = authorName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
