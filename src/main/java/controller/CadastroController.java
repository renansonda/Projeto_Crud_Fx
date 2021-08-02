package controller;

import dao.UsersDAO;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Users;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {

    @FXML
    TextField textID;
    @FXML
    TextField textCliente;
    @FXML
    TextField textAge;
    @FXML
    TextField textLogin;
    @FXML
    TextField textPassword;
    @FXML
    TableView<Users> usersTable;
    @FXML
    TableColumn<Users, String> columnName;
    @FXML
    TableColumn<Users, Integer> columnAge;
    @FXML
    TableColumn<Users, String> columnID;

    public void save(ActionEvent event) throws SQLException {

            Connection connection;
            PreparedStatement stmt;

            connection = DbConnection.getConnectionSqlite();

            String save = "insert into users (name, id, age, login, password) values (?, ?, ?, ?, ?)";  //INTERROGAÇÃO SERAM PASSADOS PARAMETROS

            stmt = connection.prepareStatement(save);

            stmt.setString(1, textCliente.getText().toString());
            stmt.setInt(2, Integer.parseInt(textID.getText().toString()));
            stmt.setString(3, textAge.getText().toString());
            stmt.setString(4, textLogin.getText().toString());
            stmt.setString(5, textPassword.getText().toString());

            stmt.execute();
            connection.close();
        }


    public void retrieve(ActionEvent event) throws SQLException {
        if(!textID.getText().isEmpty()){
            int id = Integer.parseInt(textID.getText().toString());
            Users user = UsersDAO.retrive(id);
            textCliente.setText(user.getName());
            //textID.setText(user.getId());
            textAge.setText(user.getAge());
            textLogin.setText(user.getLogin());
            textPassword.setText(user.getPassword());

        }else{
            System.out.println("Error");
        }

    }
    public void cancel(ActionEvent event){
        //LIMPA TODOS OS CAMPOS
        textCliente.setText("");
        textID.setText("");
        textAge.setText("");
        textLogin.setText("");
        textPassword.setText("");
    }
    public void delete(ActionEvent event)throws SQLException{
            Connection connection;
            PreparedStatement stmt;
            connection = DbConnection.getConnectionSqlite();
            String delete = "delete from users where id = ?";
            stmt = connection.prepareStatement(delete);
            stmt.setInt(1, Integer.parseInt(textID.getText().toString()));
            System.out.printf("Registro Deletado!");
            stmt.execute();
            connection.close();
    }

    public void update(ActionEvent event) throws  SQLException{

        Connection connection;
        PreparedStatement stmt;
        connection = DbConnection.getConnectionSqlite();

        String update = "update users set name = ?, age = ?, login = ?, password = ? where id = ?";
        stmt = connection.prepareStatement(update);

        stmt.setString(1, textCliente.getText().toString());
        stmt.setString(2, textAge.getText().toString());
        stmt.setString(3, textLogin.getText().toString());
        stmt.setString(4, textPassword.getText().toString());
        stmt.setInt(5, Integer.parseInt(textID.getText().toString()));

        System.out.printf("Atualizado");

        stmt.execute();
        connection.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Usuário logado: ");
        System.out.println(LoginController.usuarioLogado.getName());
        System.out.println(LoginController.usuarioLogado.getId());
        System.out.println(LoginController.usuarioLogado.getAge());

        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        try {
            usersTable.setItems(usersList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        try {
            usersTable.setItems(usersList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        try {
            usersTable.setItems(usersList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    private ObservableList<Users> usersList() throws SQLException {
        return FXCollections.observableArrayList(UsersDAO.listAll());
    }
}
