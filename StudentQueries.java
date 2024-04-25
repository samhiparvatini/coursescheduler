/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sammyparvatini
 */
package CourseScheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentID, firstName, lastName) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentList = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select studentID, firstName, lastName from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry student = new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
                studentList.add(student);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentList;
        
    }
    
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        String studentIdentifier = "";
        String studentFirstName = "";
        String studentLastName = "";
        // StudentEntry student = new StudentEntry("", "", "");
        try {
            getStudent = connection.prepareStatement("select studentID, firstName, lastName from app.student Where studentID=(?)");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery(); 
            
            // student.setStudentID(resultSet.getString(1));
            // student.setFirstName(resultSet.getString(2));
            // student.setLastName(resultSet.getString(3));
            if (resultSet.next()) {
                studentIdentifier = resultSet.getString(1);
                studentFirstName = resultSet.getString(2);
                studentLastName = resultSet.getString(3);
            }  
        }
         
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        StudentEntry student = new StudentEntry(studentIdentifier,studentFirstName,studentLastName); 
            
        
        return student;
        
    }
    
    public static String getStudentDetails(String studentID) {
        connection = DBConnection.getConnection();
        
        String studentDetails = "";

        try {
            getStudent = connection.prepareStatement("select studentID, firstName, lastName from app.student Where studentID=(?)");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery(); 
            
            // student.setStudentID(resultSet.getString(1));
            // student.setFirstName(resultSet.getString(2));
            // student.setLastName(resultSet.getString(3));
            if (resultSet.next()) {
                studentDetails = resultSet.getString(2) + " " + resultSet.getString(3);
            }  
        }
         
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        // StudentEntry student = new StudentEntry(studentIdentifier,studentFirstName,studentLastName); 
            
        
        return studentDetails;
        
    }
    
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        try {
            dropStudent = connection.prepareStatement("delete from app.student where studentID=(?)");
            dropStudent.setString(1, studentID);
            
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}

