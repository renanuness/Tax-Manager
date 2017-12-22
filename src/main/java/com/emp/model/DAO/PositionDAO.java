package com.emp.model.DAO;

import com.emp.model.domain.Employee;
import com.emp.model.domain.Position;
import javafx.geometry.Pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean InsertPosition(Position position){
        String sql = "INSERT INTO POSITION (position) values(?)";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,position.getPostion());
            System.out.println(position.getPostion());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean DeletePosition(Position position){
        String sql = "DELETE FROM POSITION WHERE id=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,position.getId());
            stm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Position SelectPosition(int id){
        String sql = "SELECT * FROM POSITION WHERE id=?";
        Position response = new Position();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,id);
            ResultSet resultSet =  stm.executeQuery();
            if(resultSet.next()){
                response.setId(resultSet.getInt(1));
                response.setPostion(resultSet.getString(2));
                return response;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Position> SelectAllPositions(){
        String sql = "SELECT * FROM POSITION";
        List<Position> response = new ArrayList<Position>();
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                Position position = new Position();
                position.setId(resultSet.getInt(1));
                position.setPostion(resultSet.getString(2));
                System.out.println(resultSet.getString(1));
                response.add(position);
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean AlterPosition(Position position){
        String sql = "UPDATE POSITION SET position=? WHERE id=?";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,position.getPostion());
            stm.setInt(2,position.getId());
            stm.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
