package CourseScheduler;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sammyparvatini
 */
public class CourseEntry {
    public String semester;
    public String courseCode;
    public String description;
    public int seats;
    
    public CourseEntry (String nameSemester, String code, String info, int vacancy) {
        semester = nameSemester;
        courseCode = code;
        description = info;
        seats = vacancy;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getSeats() {
        return seats;
    }
}