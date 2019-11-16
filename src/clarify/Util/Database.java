package clarify.Util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

    public static Connection conn;

    //Opens connection to the database
    public static void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:INFS2605.db");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //Executes result set based on @param
    public ResultSet getResultSet(String sqlstatement) throws SQLException {
        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet RS = statement.executeQuery(sqlstatement);
        return RS;
    }

    //Inserts @param on the database
    public void insertStatement(String insert_query) throws SQLException {
        java.sql.Statement stmt = null;
        openConnection();
        try {
            conn.setAutoCommit(false);
            System.out.println("Database opened successfully");
            stmt = conn.createStatement();
            System.out.println("The query was: " + insert_query);
            stmt.executeUpdate(insert_query);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        stmt.close();
    }

    //This method creates the Categories table
    public void createCategoriesTable() {
        PreparedStatement createLoginTable = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking Categories table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "Categories", null);
            if (!rs.next()) {
                //Limiting the length of category names to otherwise gg
                String ps1 = "CREATE TABLE Categories "
                        + "(cat_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "category_name TEXT NOT NULL, "
                        + "colour TEXT NOT NULL);";
                System.out.println("Categories table created");
                
                /* Inserts demo data - NOTE: We use rgb code as the data for colour
                #ffffff = White, #2196F3 = Light Blue */
                ArrayList<String> stList = new ArrayList<String>();
                stList.add(ps1);
                stList.add("INSERT INTO Categories(cat_id, category_name, colour) "
                    + "VALUES ('1', 'Home', '#FF0000');");
                stList.add("INSERT INTO Categories(cat_id, category_name, colour) "
                    + "VALUES ('2', 'Work', '#FF8000');");
                stList.add("INSERT INTO Categories(cat_id, category_name, colour) "
                    + "VALUES ('3', 'University', '#FFFF00');");
                stList.add("INSERT INTO Categories(cat_id, category_name, colour) "
                    + "VALUES ('4', 'Societies', '#00FF00');");
                stList.add("INSERT INTO Categories(cat_id, category_name, colour) "
                    + "VALUES ('5', 'Misc', '#3333FF');");
                for (String st: stList){
                    insertStatement(st);
                }
            } else {
                System.out.println("Categories table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEntriesTable() {
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking ENTRIES table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "ENTRIES", null);
            if (!rs.next()) {
                ArrayList<String> statementString = new ArrayList<String>();
                statementString.add("CREATE TABLE ENTRIES "
                        + " (ent_id INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ",startTime TEXT NOT NULL"
                        + ",endTime TEXT NOT NULL"
                        + ",entryDescription TEXT NOT NULL"
                        + ",category INTEGER NOT NULL"
                        + ",task INTEGER NOT NULL"
                        + ",FOREIGN KEY(category) REFERENCES Categories(cat_id)"
                        + ",FOREIGN KEY(task) REFERENCES TASKS(task_id)"
                        + ");");
                System.out.println("Entries table created");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-16 12:00:00', '2019-11-17 23:00:00', 'First entry', '1', '1');");
                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-16 10:00:00', '2019-11-18 21:00:00', 'Another entry', '2', '3');");
                
                 statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-17 12:00:00', '2019-11-18 22:00:00', 'Study for exam', '3', '5');");
                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-17 10:00:00', '2019-11-17 23:00:00', 'Work out', '5', '4');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-18 10:00:00', '2019-11-18 23:00:00', 'Watch Lectures', '1', '2');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-18 10:00:00', '2019-11-18 23:00:00', 'Do homework', '2', '2');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + "VALUES ('2019-11-19 10:00:00', '2019-11-19 20:50:00', 'Eat', '3', '2');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-20 10:00:00', '2019-11-20 20:01:00', 'Lowest', '5', '1');");
                
                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-21 12:00:00', '2019-11-21 23:00:00', 'First entry', '1', '1');");
                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-21 10:00:00', '2019-11-21 21:00:00', 'Another entry', '2', '3');");
                
                 statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-22 12:00:00', '2019-11-23 22:00:00', 'Study for exam', '3', '5');");
                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-23 10:00:00', '2019-11-24 23:00:00', 'Work out', '5', '4');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-23 10:00:00', '2019-11-24 23:00:00', 'Watch Lectures', '5', '2');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-24 10:00:00', '2019-11-18 23:00:00', 'Do homework', '5', '2');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + "VALUES ('2019-11-25 10:00:00', '2019-11-26 20:50:00', 'Eat', '5', '2');");

                statementString.add(
                        "INSERT INTO ENTRIES (startTime, endTime, entryDescription, category, task)"
                        + " VALUES ('2019-11-23 10:00:00', '2019-11-24 20:01:00', 'Lowest', '5', '1');");

                for (String thisStatement : statementString) {
                    try {
                        insertStatement(thisStatement);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("ENTRIES TABLE already Exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTaskTable() {
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking TASKS table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "TASKS", null);
            if (!rs.next()) {
                ArrayList<String> statementString = new ArrayList<String>();
                statementString.add("CREATE TABLE TASKS "
                        + "(task_id INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ",task_title TEXT NOT NULL"
                        + ",task_desc TEXT"
                        + ",do_date TEXT"
                        + ",due_date TEXT"
                        + ",priority INTEGER NOT NULL"
                        + ",status INTEGER NOT NULL"
                        + ");");
                System.out.println("Tasks table created");
                
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Go to the gym', 'Never skip leg day', '2019-11-17 12:00:00', '2019-11-17 12:00:00', '100', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Write code for database', 'Create tables for Data Capture', '2019-11-17 12:00:00','2019-11-17 12:00:00','95', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Complete Wireframes', 'Draw up wireframes for 2605', '2019-11-18 12:00:00', '2019-11-18 12:00:00', '90', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Attend meeting', 'Meeting involves agenda on computer', '2019-11-18 12:00:00','2019-11-18 12:00:00','85', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Never dog the boys', 'YTB', '2019-11-19 12:00:00', '2019-11-19 12:00:00', '43', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('BITSA', 'Gotta do shit for BITSA', '2019-11-19 12:00:00','2019-11-19 12:00:00','32', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Vote for ___', 'Vote [1] for asdsf', '2019-11-20 12:00:00', '2019-11-20 12:00:00', '65', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Kill this Love', 'What is love?', '2019-11-20 12:00:00','2019-11-20 12:00:00','34', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Dont know what to do without you', 'sfasfs', '2019-11-21 12:00:00', '2019-11-21 12:00:00', '90', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('BITSA', 'Gotta do shit for BITSA', '2019-11-21 12:00:00','2019-11-21 12:00:00','43', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Ddu-ddu ddu-ddu', 'Gonna hit you with the', '2019-11-22 12:00:00', '2019-11-22 12:00:00', '23', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('ENACTUS', 'I like littering', '2019-11-22 12:00:00','2019-11-22 12:00:00','85', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('180DC Consulting', 'I like consulting', '2019-11-23 12:00:00', '2019-11-23 12:00:00', '90', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('ENACTUS 2', 'I like littering not', '2019-11-23 12:00:00','2019-11-23 12:00:00','58', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('VSA', 'VSA for the boys', '2019-11-24 12:00:00', '2019-11-24 12:00:00', '90', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('TFT', 'Shout a little legend', '2019-11-24 12:00:00','2019-11-24 12:00:00','58', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Yeet', 'Yeet haw', '2019-11-25 12:00:00', '2019-11-25 12:00:00', '90', '0');");
                statementString.add(
                        "INSERT INTO TASKS (task_title, task_desc, do_date, due_date, priority, status) "
                        + "VALUES ('Tegridy Farms', 'Gawd damn it', '2019-11-25 12:00:00','2019-11-25 12:00:00','58', '0');");
                
                for (String thisStatement : statementString) {
                    try {
                        insertStatement(thisStatement);
                    } catch (SQLException e) {

                    }
                }
            } else {
                System.out.println("Tasks TABLE already Exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void createReflectionTable() throws SQLException { 
        PreparedStatement tableImplement = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking REFLECTION Table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "Reflection", null);
            if(!rs.next()){
                ArrayList<String> statementString = new ArrayList<String>();
                String createStatement = "CREATE TABLE Reflection "
                        + "(ref_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "wellAnswer TEXT NOT NULL,"
                        + "betterAnswer TEXT NOT NULL,"
                        + "currentDate TEXT NOT NULL);";
                statementString.add(createStatement);
                
                statementString.add(
                        "INSERT INTO Reflection (wellAnswer, betterAnswer, currentDate)"
                        + " VALUES ('I finished things quicker', 'Could do them more accurately', '2019-11-08');");
                statementString.add(
                        "INSERT INTO Reflection (wellAnswer, betterAnswer, currentDate)"
                        + " VALUES ('I finished things quicker', 'Could do them more accurately', '2019-11-10');");  
                statementString.add(
                        "INSERT INTO Reflection (wellAnswer, betterAnswer, currentDate)"
                        + " VALUES ('I finished things quicker', 'Could do them more accurately', '2019-11-11');");  
                statementString.add(
                        "INSERT INTO Reflection (wellAnswer, betterAnswer, currentDate)"
                        + " VALUES ('I did nothing', 'Could do something', '2019-11-12');");
                statementString.add(
                        "INSERT INTO Reflection (wellAnswer, betterAnswer, currentDate)"
                        + " VALUES ('I did nothing', 'Could do something', '2019-11-14');");
                statementString.add(
                        "INSERT INTO Reflection (wellAnswer, betterAnswer, currentDate)"
                        + " VALUES ('I did something', 'Could do them more accurately', '2019-11-15');");
                
                for (String thisStatement : statementString) {
                    try {
                        insertStatement(thisStatement);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                
                System.out.println("Reflection table created");
            } else {
                System.out.println("REFLECTION TABLE already Exists.");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
