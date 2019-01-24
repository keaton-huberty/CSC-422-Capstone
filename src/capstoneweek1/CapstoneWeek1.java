/*
Capstone Team 1n 
 */
package capstoneweek1;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Keaton, Will, Mike, and Amin 1/21/2019
 */
public class CapstoneWeek1 extends Application {

    private final TextField tfUsername = new TextField();
    private final TextField tfPassword = new TextField();
    private final Label lbUsername = new Label("Username");
    private final Label lbPassword = new Label("Password");
    private final Button btnLogin = new Button("Login");
    
    // New comment to test pushing to GitHub

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Datbase Utility object used for checking login credentials
        // This database is connected to my localhost:3306.  I believe its the default port 
        // when setting up a database locally using phpmyadmin
        DBUtility db = new DBUtility();

        //The vertical Box holds the text fields, labels, and the log in button
        VBox vBox = new VBox();
        VBox vBoxDashboard = new VBox();
        
        // adding the labesl, textfields and button to the vertical box
        vBox.getChildren().addAll(tfUsername, lbUsername, tfPassword, lbPassword, btnLogin);
        vBox.setAlignment(Pos.CENTER);

        // Adding the vertical box to the scene
        Scene scene = new Scene(vBox, 250, 350);

        
        // this is the listener that calls the function to check if the username matches the password
        // if correct, the window will close and a new window will open that will be the user's dashboard
        btnLogin.setOnAction((javafx.event.ActionEvent e) -> {

            // connecting to the database
            try {
                db.dbConnect();
            } catch (SQLException ex) {
                Logger.getLogger(CapstoneWeek1.class.getName()).log(Level.SEVERE, null, ex);
            }

            // This method, "checkLogin", takes the text entered in the username and password field and passes
            // them along as strings.  Click over to the DBUtility class to see the method.
            // It is a boolean.  If returns true, it opens a new window, the future dashboard
            // if false, it will pop up with a error message (not complete)
            try {
                if (db.checkLogin(tfUsername.getText(), tfPassword.getText())) {

                    Scene dashboard = new Scene(vBoxDashboard, 600, 400);
                    Stage dashboardStage = new Stage();
                    dashboardStage.setTitle("Welcome " + tfUsername.getText() + "!");
                    dashboardStage.setScene(dashboard);
                    primaryStage.close();
                    dashboardStage.show();
                } else {
                    System.out.println("Wrong password or username");

                    Scene loginErrorScene = new Scene(vBoxDashboard, 600, 400);
                    Stage loginErrorStage = new Stage();
                    loginErrorStage.setTitle("WRONG USERNAME OR PASSWORD!");
                    loginErrorStage.setScene(loginErrorScene);
                    loginErrorStage.show();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CapstoneWeek1.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        primaryStage.setTitle("Social Gaming Pro!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}
