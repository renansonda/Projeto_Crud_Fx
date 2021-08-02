package controller;

import dao.UsersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController  {
    @FXML
    TextField textLogin;
    @FXML
    TextField textPassword;
    public static Users usuarioLogado;

    public void login(ActionEvent event){
        try {
            List<Users> usersList = UsersDAO.listAll();
            System.out.println("Total de usuários na lista: " + usersList.size());
            //Validar login e carregar tela
            String login = textLogin.getText().toString();
            String password = textPassword.getText().toString();
            boolean status = false;
            //percorrer a lista e achar o login
            for(Users u : usersList){
                if(login.equals(u.getLogin()) && password.equals(u.getPassword())){
                    status = true;
                    usuarioLogado = u;
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cadastro.fxml"));
                    stage.setScene(new Scene(root));
                    stage.setTitle("My modal window");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
                    stage.show();
                    break;
                }else{
                    status = false;
                }
            }
            if(!status){
                System.out.println("Login incorreto!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel(ActionEvent event){}

}
