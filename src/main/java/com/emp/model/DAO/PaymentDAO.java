package com.emp.model.DAO;

import com.emp.model.domain.Employee;
import com.emp.model.domain.Payment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean InsertPayment(List<Payment> payment){
        String sql = "INSERT INTO PAYMENT (employeeID,workDay,workedHours,hourRate) values(?,?,?,?)";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);

            for(int i=0; i<payment.size(); i++) {
                System.out.println("INSERT:"+payment.get(i).getEmployee().getName()+"/"+payment.get(i).getDatePayment());

                stm.setInt(1, payment.get(i).getEmployee().getId());
                stm.setDate(2, Date.valueOf(payment.get(i).getDatePayment()));
                stm.setInt(3, payment.get(i).getHours());
                stm.setFloat(4, payment.get(i).getPayRate());
                stm.execute();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeletePayment(Payment payment){
        return false;
    }

    public boolean AlterPayment(Payment payment){
        return false;

    }

    public List<Payment> SelectEmployeePayments(Employee employee){
        String sql = "SELECT * FROM PAYMENT WHERE employeeID=? ORDER BY workDay ASC";
        ResultSet resultSet;
        List<Payment> listAllPayments = new ArrayList<>();
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,employee.getId());
            stm.execute();
            resultSet = stm.getResultSet();
            EmployeeDAO employeeDAO = new EmployeeDAO();
            employeeDAO.setConnection(connection);
            while (resultSet.next()){
                Payment payment = new Payment();
                payment.setEmployee(employee);
                payment.setDatePayment(resultSet.getDate(3).toLocalDate());
                payment.setHours(resultSet.getInt(4));
                payment.setPayRate(resultSet.getFloat(5));
                listAllPayments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAllPayments;
    }


    public List<Payment> SelectPaymenteFromTo(LocalDate iniPayment, LocalDate finalPayment, Employee employee){
        ResultSet resultSet;
        Date d1 = Date.valueOf(iniPayment.toString());
        Date d2 = Date.valueOf(finalPayment.toString());
        String sql = "SELECT * FROM PAYMENT WHERE employeeID=? AND workDay BETWEEN DATE('"+d1+"') AND DATE('"+d2+"')";
        System.out.println("SQL:"+sql);
        List<Payment> listAllPayments = new ArrayList<>();
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,employee.getId());


            stm.execute();
            resultSet = stm.getResultSet();
            while (resultSet.next()){
                Payment payment = new Payment();
                payment.setEmployee(employee);
                payment.setDatePayment(resultSet.getDate(3).toLocalDate());
                payment.setHours(resultSet.getInt(4));
                payment.setPayRate(resultSet.getFloat(5));
                listAllPayments.add(payment);
                System.out.println("PAYMENT:"+payment.getDatePayment());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAllPayments;
    }

    public List<Payment> SelectAllPayments(LocalDate iniPayment, LocalDate finalPayment){
        ResultSet resultSet;
        Date d1 = Date.valueOf(iniPayment.toString());
        Date d2 = Date.valueOf(finalPayment.toString());
        String sql = "SELECT * FROM PAYMENT WHERE workDay BETWEEN DATE('"+d1+"') AND DATE('"+d2+"')";
        System.out.println("SQL:"+sql);
        List<Payment> listAllPayments = new ArrayList<>();
        try{
            PreparedStatement stm = connection.prepareStatement(sql);


            stm.execute();
            resultSet = stm.getResultSet();
            while (resultSet.next()){
                Payment payment = new Payment();
                payment.setDatePayment(resultSet.getDate(3).toLocalDate());
                payment.setHours(resultSet.getInt(4));
                payment.setPayRate(resultSet.getFloat(5));
                listAllPayments.add(payment);
                System.out.println("PAYMENT:"+payment.getDatePayment());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAllPayments;
    }

}
