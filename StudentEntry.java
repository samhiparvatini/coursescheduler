package CourseScheduler;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sammyparvatini
 */
public class StudentEntry {
    
    public String studentID;
    public String firstName;
    public String lastName;
    
    
    public StudentEntry(String studentIdentifier, String studentFirstName, String studentLastName) {
        studentID = studentIdentifier;
        firstName = studentFirstName;
        lastName = studentLastName;
    }

    public String getStudentID() {
        return studentID; 
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    
}
