/*
Capstone Team 1n 
 */
package capstoneweek1;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Keaton, Will, Mike, and Amin 1/21/2019
 *
 */
public class CapstoneWeek1 extends Application {

    private final TextField tfUsername = new TextField();
    private final PasswordField tfPassword = new PasswordField();
    private final Label lbUsername = new Label("Username");
    private final Label lbPassword = new Label("Password");
    private final Button btnLogin = new Button("Login");
    private final Button btnCreateAccount = new Button("Create Account");
    private final ImageView imgViewLogo = new ImageView();
    private final Image imgLogo = new Image("background.png");

    // New comment to test pushing to GitHub
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Datbase Utility object used for checking login credentials
        // This database is connected to my localhost:3306.  I believe its the default port 
        // when setting up a database locally using phpmyadmin
        DBUtility db = new DBUtility();

        //The vertical Box holds the text fields, labels, and the log in button
        VBox vBox = new VBox();
        StackPane logoStackpane = new StackPane();
        imgViewLogo.setImage(imgLogo);
        imgViewLogo.setFitHeight(150);
        imgViewLogo.setPreserveRatio(true);
        logoStackpane.getChildren().add(imgViewLogo);
        logoStackpane.setPadding(new Insets(25));
        //imgViewLogo.set

//        tfUsername.setMaxWidth(200);
//        tfPassword.setMaxWidth(200);
        //VBox vBoxDashboard = new VBox();
        // adding the labesl, textfields and button to the vertical box
        vBox.getChildren().addAll(logoStackpane, tfUsername, lbUsername, tfPassword, lbPassword, btnLogin, btnCreateAccount);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        //vBox.set

        // Adding the vertical box to the scene
        Scene scene = new Scene(vBox, 250, 450);

        // adding the stylesheet
        scene.getStylesheets().add(CapstoneWeek1.class.getResource("Login.css").toExternalForm());
        primaryStage.show();
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
                    
                    primaryStage.close();
                    launchDashboard(tfUsername.getText());
                    
                    //db.dbClose();
                    //

                } else {
                    loginError();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CapstoneWeek1.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        primaryStage.setTitle("Social Gaming Pro!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void loginError() {
        VBox errorVbox = new VBox();
        Label lbError = new Label("Wrong Username or Password!");
        errorVbox.getChildren().add(lbError);
        errorVbox.setAlignment(Pos.CENTER);

        Scene loginErrorScene = new Scene(errorVbox, 200, 200);
        Stage loginErrorStage = new Stage();
        loginErrorStage.setTitle("ALERT!");
        loginErrorStage.setScene(loginErrorScene);
        loginErrorStage.show();

    }

    public void launchDashboard(String strName) {

        Stage dashboardStage = new Stage();
        //sets title at top of window
        dashboardStage.setTitle("SocialGamer Pro");

        //set up left/top pane
        //set up profile picture
        Image profilePic = new Image("userPic.png");
        ImageView profilePicView = new ImageView(profilePic);
        profilePicView.setPreserveRatio(true);
        profilePicView.setFitHeight(150);
        //vbox for holding name over current game
        VBox nameAndGame = new VBox();
        Text name = new Text(strName);
        name.setStyle("-fx-font: 24 arial;");
        Text currentGame = new Text("Now Playing: Call of Duty: Modern Warfare 2");
        currentGame.setStyle("fx-font: 16 arial;");
        nameAndGame.getChildren().addAll(name, currentGame);
        //flow pane for holding picture, name/game, and messages button
        FlowPane topPane = new FlowPane();
        Text tab = new Text("\t   ");
        topPane.getChildren().addAll(tab, profilePicView, nameAndGame);
        topPane.setHgap(10);
        nameAndGame.setAlignment(Pos.CENTER_LEFT);

        Image tableExample = new Image("sampleTable.png");
        ImageView tableView = new ImageView(tableExample);
        profilePicView.setPreserveRatio(true);
        VBox leftVbox = new VBox();
        Text bioLabel = new Text("\tUser Biography");
        bioLabel.setStyle("-fx-font: 24 arial;");
        Text userBio = new Text("\t   Hello, my name is Will S. I have been playing video games for most of my life.\n"
                + "\t   My favorite games include MMOs, RPGs, and sports games.\n"
                + "\t   I am a senior in college and live in Minnesota.");
        userBio.setStyle("-fx-font: 18 arial;");
        Text cLabel = new Text("\tGames I Play");
        cLabel.setStyle("-fx-font: 24 arial;");
        Button btnAddGame = new Button("Add Game");
        Text btnLabel1 = new Text("");
        btnAddGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                btnLabel1.setText("Functionality coming soon.");
            }
        });
        Text tab2 = new Text("\t   ");
        HBox table = new HBox();
        table.getChildren().addAll(tab2, tableView);
        Text tab3 = new Text("\t   ");
        HBox btn = new HBox();
        btn.getChildren().addAll(tab3, btnAddGame);
        Separator horizSep = new Separator();
        horizSep.setOrientation(Orientation.HORIZONTAL);
        leftVbox.getChildren().addAll(topPane, bioLabel, userBio, cLabel, table, btn, btnLabel1);
        leftVbox.setAlignment(Pos.TOP_LEFT);
        leftVbox.setSpacing(10);

        //set up right pane for friends/messages
        //white space for formatting reasons
        Text whiteSpace = new Text("\n");
        Text whiteSpace2 = new Text("\n");
        //button for messages, doesn't really function yet
        Button btnMessages = new Button("Messages");
        Text btnLabel2 = new Text("");
        btnMessages.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                btnLabel2.setText("No messages");
            }
        });
        btnMessages.setStyle("-fx-background-color: #F7C4C1; -fx-border-color: #000000; -fx-font-size: 2em;");
        //btnMessages.setStyle("-fx-border-color: #000000;");
        VBox btnVbox = new VBox();
        btnVbox.getChildren().addAll(btnMessages, btnLabel2);
        VBox rightVbox = new VBox();
        rightVbox.setSpacing(10);
        Text flLabel = new Text("Friends");
        flLabel.setStyle("-fx-font: 24 arial;");

        Text friends = new Text("1. Mike\n2. Amin\n3. Keaton\n");
        friends.setStyle("-fx-font: 20 arial;");
        rightVbox.getChildren().addAll(whiteSpace, btnVbox, whiteSpace2, flLabel, horizSep, friends);

        //set up bottom pane
        Text bottomText = new Text("Created by Keaton, Will, Mike, and Amin (2019)");

        //create border pane with each part as set up above
        BorderPane bPane = new BorderPane();
        bPane.setRight(rightVbox);
        bPane.setBottom(bottomText);
        bPane.setLeft(leftVbox);
        //width, height of actual scene
        Scene dashboard = new Scene(bPane, 1100, 650);

        dashboardStage.setScene(dashboard);
        dashboardStage.setMinHeight(650);
        dashboardStage.setMinWidth(1100);

        //primaryStage.close();
        dashboardStage.show();
        //set background color to a light grey
        bPane.setStyle("-fx-background-color: #DCDCDC;");

    }

    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}
