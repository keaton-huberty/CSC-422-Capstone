/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneweek1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Will
 */
public class Dashboard {

    private String userName, fName, lName, bio;

    public Dashboard(String userName, String fName, String lName, String bio) {
        this.userName = userName;
        this.fName = fName;
        this.lName = lName;
        this.bio = bio;
    }

    ;
    
    public void launchDashboard() throws SQLException {

        Stage dashboardStage = new Stage();
        //sets title at top of window
        dashboardStage.setTitle("SocialGamer Pro");

        //set up left/top pane
        //set up profile picture
        Image profilePic = new Image("userPic.png");
        ImageView profilePicView = new ImageView(profilePic);
        profilePicView.setPreserveRatio(true);
        profilePicView.setFitHeight(150);
        //setup home button icon
        Image homeIcon = new Image("home.png");
        ImageView homeView = new ImageView(homeIcon);
        homeView.setPreserveRatio(true);
        homeView.setFitHeight(20);
        Button homeButton = new Button();
        Button btnViewFollower = new Button("View Profile");
        Button btnViewFollowing = new Button("View Profile");
        //Button btnLogout = new Button("Logout");

        homeButton.setGraphic(homeView);
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
        //add to top pane
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
        
        //Logout Button
        Button btnLogout = new Button("Logout");
        //Text btnLabel3 = new Text("");
        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dashboardStage.close();

            //Code for returning to the login page will go here
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
        leftVbox.getChildren().addAll(topPane, bioLabel, btnLogout, userBio, cLabel, table, btn, btnLabel1);
        leftVbox.setAlignment(Pos.TOP_LEFT);
        leftVbox.setSpacing(10);

        //set up right pane for friends/messages
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
        Text flLabel = new Text("Followers");
        flLabel.setStyle("-fx-font: 24 arial;");

        Text lable = new Text("Received Msgs");
        //send button
        Button sendButton = new Button("Send");
        //making a text area
        TextArea textArea = TextAreaBuilder.create()
                .prefWidth(100)
                .wrapText(true)
                .build();
        //chatbox code here

        //adding a scroll pane for scrolling in text area
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        //setting height and width
        scrollPane.setPrefWidth(100);
        scrollPane.setPrefHeight(150);

        //text area for typing msg
        TextArea msgType = new TextArea();
        msgType.setPrefHeight(10);
        msgType.setPrefWidth(100);
        //set up list to hold followers
        ListView friendsList = new ListView();
        DBUtility dbobj = new DBUtility();
        ResultSet userFriends = dbobj.getFriends();
        while (userFriends.next()) {
            friendsList.getItems().add(
                    userFriends.getString("userName")//adding users in drop down from database
            );

        }
        //setting place holder
        msgType.setPromptText("Type Msg here");

        //        creating dropdown for friend selection
        ComboBox friends1 = new ComboBox();
//        friends1.getItems().addAll(
//                                    "Mike",
//                                    "Amin",
//                                    "Keaton"
//                                );
        //getting all users from database to fill the dropdown
        //DBUtility dbobj=new DBUtility();
        ResultSet users = dbobj.getUsers();
        while (users.next()) {
            friends1.getItems().addAll(
                    users.getString("userName")//adding users in drop down from database
            );

        }

        //adding listener to send button
        sendButton.setOnAction((javafx.event.ActionEvent e) -> {
            boolean isMyComboBoxEmpty = friends1.getSelectionModel().isEmpty();//to check if user is selected from dropdown
            String receiver = String.valueOf(friends1.getValue());//getting value of selected user from dropdown
            //condition for check user is selected other show alert
            if (isMyComboBoxEmpty) {
                Alert alert = new Alert(AlertType.INFORMATION, "Please Select User From DropDown!", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
                return;
            }
            String msg = msgType.getText();//getting value of msg
            //checking if msg box is empty
            if (msg.compareTo("") == 0) {
                Alert alert = new Alert(AlertType.INFORMATION, "Enter Text in Msg Box!", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
                return;
            }
            try {
                dbobj.insertMsg(userName, receiver, msg);//calling insertmsg method frm DBUtility
                msgType.setText("");
                msgType.setPromptText("Type Msg here");
                //creating alert for msg sent
                Alert alert = new Alert(AlertType.INFORMATION, "Message Sent!", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();

            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //setting text in msg box for recieved msg
        ResultSet msgs = dbobj.getMsg(userName);
        String setText = "";

        while (msgs.next()) {
            //setting msgs into the text box
            String from = msgs.getString("msgSender");
            String msgContent = msgs.getString("msgContent");

            setText = setText + "From " + from + ":\n" + msgContent + "\n";
            textArea.setText(setText);

        }
        //set up search bar for finding users
        ComboBox search = new ComboBox();
        ResultSet searchUsers = dbobj.getUsers();
        while (searchUsers.next()) {
            search.getItems().addAll(
                    searchUsers.getString("userName")
            );
        }

        btnViewFollower.setOnAction((javafx.event.ActionEvent e) -> {
            DBUtility db = new DBUtility();
            try {
                db.dbConnect();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                
                String friendName = friendsList.getSelectionModel().getSelectedItems().toString();
                friendName = friendName.substring(1, friendName.length() - 1);
                User user = new User(db.getUserInfo(friendName));
                user.getDashboard().friendDashboard();
                db.dbClose();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        btnViewFollowing.setOnAction((javafx.event.ActionEvent e) -> {
            DBUtility db = new DBUtility();
            try {
                db.dbConnect();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                String friendName = search.getSelectionModel().getSelectedItem().toString();
                System.out.println("friendName = " + friendName);
                //String friendName = friendsList.getSelectionModel().getSelectedItems().toString();
                //friendName = friendName.substring(1, friendName.length() - 1);
                User user = new User(db.getUserInfo(friendName));
                user.getDashboard().friendDashboard();
                db.dbClose();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        HBox searchBox = new HBox(search, homeButton);
        searchBox.setSpacing(20);
        //set search bar default value
        search.setPromptText("Find User");
        rightVbox.getChildren().addAll(searchBox, btnViewFollowing, flLabel, friendsList, btnViewFollower, friends1, lable, scrollPane, msgType, sendButton);

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
        dashboardStage.setMinWidth(1350);

        //primaryStage.close();
        dashboardStage.show();
        //set background color to a light grey
        bPane.setStyle("-fx-background-color: #DCDCDC;");

    }

    public void friendDashboard() throws SQLException {

        Stage dashboardStage = new Stage();
        //sets title at top of window
        dashboardStage.setTitle("SocialGamer Pro");

        //set up left/top pane
        //set up profile picture
        Image profilePic = new Image("userPic.png");
        ImageView profilePicView = new ImageView(profilePic);
        profilePicView.setPreserveRatio(true);
        profilePicView.setFitHeight(150);
        //setup home button icon
        Image homeIcon = new Image("home.png");
        ImageView homeView = new ImageView(homeIcon);
        homeView.setPreserveRatio(true);
        homeView.setFitHeight(20);
        Button homeButton = new Button();

        homeButton.setGraphic(homeView);
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
        //add to top pane
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
        Text btnLabel1 = new Text("");

        Text tab2 = new Text("\t   ");
        HBox table = new HBox();
        table.getChildren().addAll(tab2, tableView);
        Text tab3 = new Text("\t   ");
        HBox btn = new HBox();
        btn.getChildren().addAll(tab3);
        Separator horizSep = new Separator();
        horizSep.setOrientation(Orientation.HORIZONTAL);
        leftVbox.getChildren().addAll(topPane, bioLabel, userBio, cLabel, table, btn, btnLabel1);
        leftVbox.setAlignment(Pos.TOP_LEFT);
        leftVbox.setSpacing(10);

        //set up bottom pane
        Text bottomText = new Text("Created by Keaton, Will, Mike, and Amin (2019)");

        //create border pane with each part as set up above
        BorderPane bPane = new BorderPane();
        // bPane.setRight(rightVbox);
        bPane.setBottom(bottomText);
        bPane.setLeft(leftVbox);

        //width, height of actual scene
        Scene friendDashboard = new Scene(bPane, 1100, 650);

        dashboardStage.setScene(friendDashboard);
        dashboardStage.setMinHeight(650);
        dashboardStage.setMinWidth(1350);

        //primaryStage.close();
        dashboardStage.show();
        //set background color to a light grey
        bPane.setStyle("-fx-background-color: #DCDCDC;");

    }

}
