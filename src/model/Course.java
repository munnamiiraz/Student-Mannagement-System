package src.model;

// package model;

public class Course {
    private String teacherUsername;
    private String courseName;

    public Course() {}

    public Course(String teacherUsername, String courseName) {
        this.teacherUsername = teacherUsername;
        this.courseName = courseName;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
