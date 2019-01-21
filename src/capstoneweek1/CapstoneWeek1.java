/*
This is a place holder to put into the capstone repository on our GitHub
 */
package capstoneweek1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
Keaton, Will, Mike, and Amin
1/21/2019
 */


public class CapstoneWeek1 extends Application {
    
    private final TextField tfUsername = new TextField();
    private final TextField tfPassword = new TextField();
    private final Label lbUsername = new Label("Username");
    private final Label lbPassword = new Label("Password");
    private final Button btnLogin = new Button("Login");


    @Override
    public void start(Stage primaryStage) throws Exception {
        
        VBox vBox = new VBox();
        
        vBox.getChildren().addAll(tfUsername, lbUsername, tfPassword, lbPassword);
        
        Scene scene = new Scene(vBox, 300, 250);
        
        primaryStage.setTitle("Social Gaming Pro!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
        public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
}
