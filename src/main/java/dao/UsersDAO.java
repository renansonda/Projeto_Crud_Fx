package dao;

import database.DbConnection;
import model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    public static void create(Users user) throws SQLException {
        Connection connection;
        PreparedStatement stmt;
        connection = DbConnection.getConnectionSqlite();
        String save = "insert into users (name, age, login, password) values (?, ?, ?, ?) whem id ='1'";
        stmt = connection.prepareStatement(save);
        //Receber dados do parametro
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getAge());
        stmt.setString(3, user.getLogin());
        stmt.setString(4, user.getPassword());
        stmt.execute();
        connection.close();
    }

    public static Users retrive(int id) throws SQLException {
        Connection connection;
        PreparedStatement stmt;
        ResultSet rst;
        connection = DbConnection.getConnectionSqlite();
        String search = "select *from users where id = ?";
        stmt = connection.prepareStatement(search);
        stmt.setInt(1, id);
        //Receber resultado da consulta
        rst = stmt.executeQuery();
        Users user = new Users();
        while(rst.next()){
            user.setId(rst.getInt("id"));
            user.setName(rst.getString("name"));
            user.setAge(rst.getString("age"));
            user.setPassword(rst.getString("password"));
            user.setLogin(rst.getString("login"));
        }
        rst.close();
        connection.close();
        return user;
    }

    public static void update(Users user) throws SQLException {
        Connection connection;
        connection = DbConnection.getConnectionSqlite();
        PreparedStatement stmt;
        if(connection != null){
            String update = "update users set name = ?, age = ?, login = ?, password = ? where id = ?";
            stmt = connection.prepareStatement(update);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getAge());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getId());
            stmt.close();
        }
    }

    public static List<Users> listAll() throws SQLException {
        Connection connection;
        connection = DbConnection.getConnectionSqlite();
        Statement stmt;
        ResultSet rst;
        stmt = connection.createStatement();
        rst = stmt.executeQuery("select *from users");
        ArrayList<Users> listUsers = new ArrayList<Users>();
        //Pegar os itens do resultset e inserir na lista
        while(rst.next()){
            Users u = new Users();
            u.setId(rst.getInt("id"));
            u.setName(rst.getString("name"));
            u.setAge(rst.getString("age"));
            u.setLogin(rst.getString("login"));
            u.setPassword(rst.getString("password"));
            listUsers.add(u);
        }
        rst.close();
        connection.close();
        return listUsers;
    }
}
