package com.emp.model.database;

import java.sql.*;

import static org.apache.derby.impl.sql.execute.ColumnInfo.DROP;

public class DatabaseCreator {

    DatabaseHandler databaseHandler = new DatabaseHandler();
    //        //driver
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        Connection connection;
        String dbName = "jdbcDatabase2";
        String connectionURL = "jdbc:derby:"+dbName+";create=true";
        ////        //database name
////        String dbName = "jdbcDatabase2";
////        //Connection jdbc:derby:jdbcDatabase2;create=true
////        String connectionURL = "jdbc:derby:"+dbName+";create=true";
    public void CreateDB() throws SQLException {
        connection = DriverManager.getConnection(connectionURL);
        databaseHandler.ConectDB();
        createTablePosition();
        createTableEmployee();
        createTablePayment();
        createUniqueIndex();
    }
    public boolean createTablePosition() {
        Statement s;
        String sql = "CREATE TABLE POSITION"
                +"(id INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                +"CONSTRAINT POSITION_PK PRIMARY KEY,"
                +"position varchar(30) NOT NULL)";
        try{
            s = connection.createStatement();
            s.execute(sql);
            return true;

        }catch(SQLException e){
            e.getSQLState();
            e.getErrorCode();
            e.printStackTrace();
            return false;
        }
    }
    public boolean createTableEmployee() {
        Statement s;
        String sql = "CREATE TABLE EMPLOYEE "
                + "(id INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                + "CONSTRAINT EMPLOYEE_PK PRIMARY KEY,"
                + "name char(30) NOT NULL,"
                + "phone int,"
                + "address char(35),"
                + "tfn int,"
                + "postCode int,"
                + "startDate date,"
                + "position int CONSTRAINT PST_FK REFERENCES position)";
        try{
            s = connection.createStatement();
            s.execute(sql);
            return true;
        }catch(SQLException e){
            e.getSQLState();
            e.getErrorCode();
            e.printStackTrace();
            return false;
        }
    }
    public boolean createTablePayment(){
        Statement s;
        String sql = "CREATE TABLE PAYMENT(id INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT PAYMENT_PK PRIMARY KEY," +
                "employeeID int CONSTRAINT employee_fk REFERENCES EMPLOYEE," +
                "workDay date not null," +
                "workedHours int not null," +
                "hourRate float not null)";
        try{
            s = connection.createStatement();
            s.execute(sql);
            return true;
        }catch(SQLException e){
            e.getSQLState();
            e.getErrorCode();
            e.printStackTrace();
            return false;
        }
    }
    private void createUniqueIndex() throws SQLException {
        Statement stm;
        String sql = "CREATE UNIQUE INDEX emplayeeDay ON PAYMENT(employeeID,workDay)";
        stm = connection.createStatement();
        stm.executeUpdate(sql);
    }
}
