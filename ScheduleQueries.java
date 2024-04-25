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

public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getSchedulebyStudent;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement getStudentStatus;
    private static PreparedStatement getWaitlistedStudent;
    private static PreparedStatement updateStudentStatus;
    private static ResultSet resultSet;
    private static ResultSet getStudentResultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, courseCode, studentID, status, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimeStamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int studentCount = 0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester=(?) and courseCode=(?)");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            resultSet.next();
            studentCount = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentCount;
    }
    
    public static ArrayList<ScheduleEntry> getSchedulebyStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getSchedulebyStudent = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule where semester=(?) and studentID=(?) order by status");
            getSchedulebyStudent.setString(1, semester);
            getSchedulebyStudent.setString(2, studentID);
            resultSet = getSchedulebyStudent.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry scheduleByStudent = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                schedule.add(scheduleByStudent);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduledStudentsList = new ArrayList<ScheduleEntry>();
        try
        {
            getSchedulebyStudent = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule where semester=(?) and courseCode=(?) and status='S'");
            getSchedulebyStudent.setString(1, semester);
            getSchedulebyStudent.setString(2, courseCode);
            resultSet = getSchedulebyStudent.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry scheduleByStudent = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                scheduledStudentsList.add(scheduleByStudent);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudentsList;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudentsList = new ArrayList<ScheduleEntry>();
        try
        {
            getSchedulebyStudent = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule where semester=(?) and courseCode=(?) and status='W'");
            getSchedulebyStudent.setString(1, semester);
            getSchedulebyStudent.setString(2, courseCode);
            resultSet = getSchedulebyStudent.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry scheduleByStudent = new ScheduleEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getTimestamp(5));
                waitlistedStudentsList.add(scheduleByStudent);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudentsList;
    }
    
    public static StudentEntry dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        StudentEntry student = new StudentEntry("", "", "");
        String studentStatus = "W";
        try {
            getStudentStatus = connection.prepareStatement("select status from app.schedule where semester=(?) and studentID=(?) and courseCode=(?) and status='S'");
            getStudentStatus.setString(1, semester);
            getStudentStatus.setString(2, studentID);
            getStudentStatus.setString(3, courseCode);
            
            getStudentResultSet = getStudentStatus.executeQuery();

            if(getStudentResultSet.next()) {
                studentStatus = "S";
            }
            
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester=(?) and studentID=(?) and courseCode=(?)");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            
            dropStudentScheduleByCourse.executeUpdate();
            
            if(studentStatus == "S") {
            
                getWaitlistedStudent = connection.prepareStatement("select studentID from app.schedule where semester=(?) and courseCode=(?) and status='W' and timestamp=(select min(timestamp) from app.schedule where semester=(?) and courseCode=(?) and status='W')");
                getWaitlistedStudent.setString(1, semester);
                getWaitlistedStudent.setString(2, courseCode);
                getWaitlistedStudent.setString(3, semester);
                getWaitlistedStudent.setString(4, courseCode);

                resultSet = getWaitlistedStudent.executeQuery();

                if(resultSet.next()) {
                    updateStudentStatus = connection.prepareStatement("update app.schedule set status='S' where semester=(?) and courseCode=(?) and studentID = (?) and status='W'");
                    updateStudentStatus.setString(1, semester);
                    updateStudentStatus.setString(2, courseCode);
                    updateStudentStatus.setString(3, resultSet.getString(1));

                    updateStudentStatus.executeUpdate();

                    student = StudentQueries.getStudent(resultSet.getString(1));
                }
            }
               
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return student;
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester=(?) and courseCode=(?)");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            updateScheduleEntry = connection.prepareStatement("update app.schedule where semester=(?) and entry=(?)");
            updateScheduleEntry.setString(1, semester);
            updateScheduleEntry.setObject(2, entry);
            updateScheduleEntry.executeUpdate();

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
