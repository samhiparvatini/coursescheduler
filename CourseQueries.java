package CourseScheduler;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sammyparvatini
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement getAllCoursesCodes;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester, courseCode, description, seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static int getCourseSeats(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int seatCount = 0;
        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester=(?) and courseCode=(?)");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            resultSet.next();
            seatCount = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seatCount;
    }
    
    public static ArrayList<String> getAllCoursesCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> codes = new ArrayList<String>();
        try
        {
            getAllCoursesCodes = connection.prepareStatement("select courseCode from app.course where semester=(?) order by courseCode");
            getAllCoursesCodes.setString(1, semester);
            resultSet = getAllCoursesCodes.executeQuery();
            
            while(resultSet.next())
            {
                codes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return codes;
        
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            getAllCourses = connection.prepareStatement("select semester, courseCode, description, seats from app.course where semester=(?) order by courseCode");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                CourseEntry course = new CourseEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
                courses.add(course);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
    
    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropCourse = connection.prepareStatement("delete from app.course where semester=(?) and courseCode=(?)");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
