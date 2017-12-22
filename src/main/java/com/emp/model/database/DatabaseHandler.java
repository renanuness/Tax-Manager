package com.emp.model.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DatabaseHandler {


    private Connection connection;
    private Statement sttm = null;

    public void CreateDB() throws SQLException {
//        //driver
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        //database name
        String dbName = "jdbcDatabase2";
        //Connection jdbc:derby:jdbcDatabase2;create=true
        String connectionURL = "jdbc:derby:"+dbName+";create=true";

        Connection conn = null;
        Statement stmt;
//        PreparedStatement psInsert;
//        ResultSet rsData;
//        String answer;

//        try {
            conn = DriverManager.getConnection(connectionURL);
//        }catch (SQLException e){
//            e.getSQLState();
//            e.getErrorCode();
//            e.printStackTrace();
//        }

////        ResultSet f = stmt.executeQuery("select * FROM POSITION");
////        f.next();

        String createString = "CREATE TABLE PAYMENT(\n" +
                "\tid INT NOT NULL\n"+
                "\tCONSTRAINT PAYMENT_PK PRIMARY KEY,\n"+
                "\temployeeID int CONSTRAINT employee_fk REFERENCES EMPLOYEE,\n" +
                "\tworkDay date not null,\n" +
                "\tworkedHours int not null,\n" +
                "\thourRate float not null,\n" +
                "\tCONSTRAINT compose_key UNIQUE(employeeID, workDay)\n" +
                ")";
//        try {
            stmt = conn.createStatement();
//            System.out.println("AVISO:"+stmt.getWarnings());
            stmt.executeQuery(createString);

//            System.out.println("CRIADA");
//        }catch (SQLException e){
//            e.printStackTrace();
//        }

////       String createString2 ="CREATE TABLE EMPLOYEE "
////                + "(id INT NOT NULL GENERATED ALWAYS AS IDENTITY "
////                +"CONSTRAINT EMPLOYEE_PK PRIMARY KEY,"
////                +"name char(30) NOT NULL,"
////                +"phone int,"
////                +"address char(35),"
////                +"tfn int,"
////                +"postCode int,"
////                +"startDate date,"
////                +"position int CONSTRAINT PST_FK REFERENCES position)";
////        try{
////            stmt = conn.createStatement();
////            stmt.execute(createString2);
////
////        }catch (SQLException e){
////            e.getSQLState();
////            e.getErrorCode();
////            e.printStackTrace();
////        }
//        //                psInsert = conn.prepareStatement("insert into WISH_LIST(WISH_ITEM) values (?)");

////        psInsert = conn.prepareStatement("INSERT INTO POSITION(position) values(?)");
////        psInsert.setString(1,"Cooker");
////        psInsert.execute();

//        conn.close();
    }

    public Connection ConectDB(){
        try{
//            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            //database name
            String dbName = "jdbcDatabase2";
            //Connection jdbc:derby:jdbcDatabase2;create=true
            String connectionURL = "jdbc:derby:"+dbName+";create=true";
            this.connection = DriverManager.getConnection(connectionURL);

            return this.connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DisconnectDB(Connection connection){
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



}
