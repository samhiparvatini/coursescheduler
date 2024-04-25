package CourseScheduler;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sammyparvatini
 */
import java.sql.Timestamp;

public class ScheduleEntry {
        
        public String semester;
        public String courseCode;
        public String studentID;
        public String status;
        public Timestamp timestamp;
        
    public ScheduleEntry(String semesterName, String courseCodeID, String studentIdentifier, String statusOfSeats, Timestamp timeAndDate) {
        semester = semesterName;
        courseCode = courseCodeID;
        studentID = studentIdentifier;
        status = statusOfSeats;
        timestamp = timeAndDate;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public String getStudentID() {
        return studentID;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Timestamp getTimeStamp() {
        return timestamp;
    }
}
