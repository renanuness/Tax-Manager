package com.emp.model.DAO;

import com.emp.model.domain.Employee;
import com.emp.model.domain.Position;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    //

    //
    public boolean InsertEmployee(Employee employee){
        String sql = "INSERT INTO EMPLOYEE (name,phone,address,tfn,postCode,startDate,position) values(?,?,?,?,?,?,?)";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,employee.getName());
            stm.setInt(2,employee.getPhone());
            stm.setString(3,employee.getAddres());
            stm.setInt(4,employee.getTfn());
            stm.setInt(5,employee.getPostCode());
            stm.setDate(6, Date.valueOf((LocalDate) employee.getStartDate()));
            stm.setInt(7,employee.getPosition().getId());
            stm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean AlterEmployee(Employee employee){
        String sql = "UPDATE EMPLOYEE SET name=?,phone=?,address=?,tfn=?,postCode=?,startDate=?,position=? WHERE id=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,employee.getName());
            stm.setInt(2,employee.getPhone());
            stm.setString(3,employee.getAddres());
            stm.setInt(4,employee.getTfn());
            stm.setInt(5,employee.getPostCode());
            stm.setDate(6, Date.valueOf((LocalDate) employee.getStartDate()));
            stm.setInt(7,employee.getPosition().getId());
            stm.setInt(8,employee.getId());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean DeleteEmployee(Employee employee){
        String sql = "DELETE FROM EMPLOYEE WHERE id=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,employee.getId());
            stm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> SelectAllEmployees(){
        String sql = "SELECT * FROM EMPLOYEE";
        List<Employee> response = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                Employee employee = new Employee();
                Position position;
                employee.setId(resultSet.getInt(1));
                employee.setName(resultSet.getString(2));
                employee.setPhone(resultSet.getInt(3));
                employee.setAddres(resultSet.getString(4));
                employee.setTfn(resultSet.getInt(5));
                employee.setPostCode(resultSet.getInt(6));
                LocalDate localDate = resultSet.getDate(7).toLocalDate();
                employee.setStartDate(localDate);
                PositionDAO positionDAO = new PositionDAO();
                positionDAO.setConnection(connection);
                position = positionDAO.SelectPosition(resultSet.getInt(8));
                employee.setPosition(position);
                response.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Employee SelectEmployee(int employeeID){
        String sql = "SELECT * FROM EMPLOYEE WHERE id=?";
        Employee employee = new Employee();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,employeeID);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()) {
                Position position;
                employee.setId(resultSet.getInt(1));
                employee.setName(resultSet.getString(2));
                employee.setPhone(resultSet.getInt(3));
                employee.setAddres(resultSet.getString(4));
                employee.setTfn(resultSet.getInt(5));
                employee.setPostCode(resultSet.getInt(6));
                LocalDate localDate = resultSet.getDate(7).toLocalDate();
                employee.setStartDate(localDate);
                PositionDAO positionDAO = new PositionDAO();
                positionDAO.setConnection(connection);
                position = positionDAO.SelectPosition(resultSet.getInt(8));
                employee.setPosition(position);

            }
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("EMPLOYEE DONT SELECTED");

            return null;
        }
    }
}
