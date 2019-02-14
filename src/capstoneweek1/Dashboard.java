/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneweek1;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Will
 */
public class Dashboard {

    private String fName, lName, bio;

    public Dashboard(String fName, String lName, String bio) {
        this.fName = fName;
        this.lName = lName;
        this.bio = bio;
    }

    ;
    
    public void launchDashboard() {

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
        Text name = new Text(fName + " " + lName);
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
        Text userBio = new Text(bio);
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

        btnMessages.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                btnLabel2.setText("No messages");
                DBUtility db = new DBUtility();

                try {
                    db.dbConnect();
                    User friend = new User(db.getUserInfo("will"));
                    dashboardStage.close();

                    friend.getDashboard().launchDashboard();
                    db.dbClose();
                } catch (SQLException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public void friendDashboard(String strName, Dashboard dashboard) {

    }

}
