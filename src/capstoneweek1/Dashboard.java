/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneweek1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
    private int userID;
    private byte[] profilePicBlob;
    private TableView gamesTable = new TableView();
    
    private Statement stmt;
    private ResultSet resultSet;

    public Dashboard(int userID, String userName, String fName, String lName, String bio) {
        this.userID = userID;
        this.userName = userName;
        this.fName = fName;
        this.lName = lName;
        this.bio = bio;
       // this.profilePicBlob = profilePic;
    }

    ;
    
    public void launchDashboard() throws SQLException, IOException {

        Stage dashboardStage = new Stage();

        //sets title at top of window
        dashboardStage.setTitle("SocialGamer Pro");
        
        DBUtility db = new DBUtility();

        db.dbConnect();
        
        Image blobPic = db.loadProfilePicture(this.userID);
        
        //set up left/top pane
        //set up profile picture
        
        Image profilePic = new Image("userPic.png");
        //ImageView profilePicView = new ImageView(profilePic);
        ImageView profilePicView = new ImageView(blobPic);
        profilePicView.setPreserveRatio(true);
        profilePicView.setFitHeight(150);

        Button btnViewFollower = new Button("View Profile");
        Button btnViewFollowing = new Button("Search User");
        //Button btnLogout = new Button("Logout");

        //vbox for holding name over current game
        VBox nameAndGame = new VBox();
        TextField name = new TextField(fName + " " + lName);
        name.setDisable(true);

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

        gamesTable.setEditable(true);

        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setMinWidth(250.0);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("gameTitle"));
        TableColumn yearColumn = new TableColumn("Release Date");
        yearColumn.setMinWidth(150.0);
        yearColumn.setCellValueFactory(
                new PropertyValueFactory<>("year"));
        TableColumn genreColumn = new TableColumn("Genre");
        genreColumn.setMinWidth(250.0);
        genreColumn.setCellValueFactory(
                new PropertyValueFactory<>("genre"));

        
        gamesTable.setItems(db.getGamesPlayed(this.userID));
        gamesTable.getColumns().addAll(titleColumn, yearColumn, genreColumn);

        profilePicView.setPreserveRatio(true);
        VBox leftVbox = new VBox();
        Text bioLabel = new Text("\tUser Biography");
        bioLabel.setStyle("-fx-font: 24 arial;");

        TextField userBio = new TextField(bio);
        userBio.setDisable(true);

        userBio.setStyle("-fx-font: 18 arial; -fx-opacity: 1.0; -fx-control-inner-background: #DCDCDC;");
        Text cLabel = new Text("\tGames I Play");
        cLabel.setStyle("-fx-font: 24 arial;");
        Button btnAddGameUser = new Button("Add Game to my List");
        Button btnAddGameLibrary = new Button("Add Game to Library");

        Button editInfo = new Button("Edit Info");
        Button updateInfo = new Button("Update Info");
        updateInfo.setVisible(false);

        //setting listner on edit info
        editInfo.setOnAction((javafx.event.ActionEvent e) -> {
            name.setDisable(false);
            userBio.setDisable(false);
            updateInfo.setVisible(true);
            editInfo.setVisible(false);
            userBio.setStyle("-fx-font: 18 arial; -fx-opacity: 1.0; -fx-control-inner-background: #FFF; ");
            name.setStyle("-fx-font: 18 arial; -fx-opacity: 1.0; -fx-control-inner-background: #FFF;");

        });


         //setting listner on update button
        updateInfo.setOnAction((javafx.event.ActionEvent e) -> {
            name.setStyle("-fx-font: 18 arial; -fx-opacity: 1.0; -fx-control-inner-background: #DCDCDC;");
            userBio.setStyle("-fx-font: 18 arial; -fx-opacity: 1.0; -fx-control-inner-background: #DCDCDC;");
            name.setDisable(true);
            userBio.setDisable(true);
            updateInfo.setVisible(false);
            editInfo.setVisible(true);

            String name1 = name.getText();
            String bio = userBio.getText();

            DBUtility dbobj = new DBUtility();

            try {
                dbobj.updateInfo(userName, name1, bio);
                Alert alert = new Alert(AlertType.INFORMATION, "Info Updated!", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        ///
        Text btnLabel1 = new Text("");
        btnAddGameLibrary.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addGameDashboard();
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
        Text tab5 = new Text("\t   ");
        HBox bioBox = new HBox();
        bioBox.getChildren().addAll(tab5, userBio);
        Text tab2 = new Text("\t   ");
        HBox table = new HBox();
        table.getChildren().addAll(tab2, gamesTable);
        Text tab3 = new Text("\t   ");
        HBox btn = new HBox();
        btn.setSpacing(8);
        btn.getChildren().addAll(tab3, btnAddGameUser, btnAddGameLibrary, editInfo, updateInfo);
        Separator horizSep = new Separator();
        horizSep.setOrientation(Orientation.HORIZONTAL);
        HBox logoutHbox = new HBox();
        Text tab4 = new Text("\t    ");
        logoutHbox.getChildren().addAll(tab4, btnLogout);
        leftVbox.getChildren().addAll(topPane, bioLabel, logoutHbox, bioBox, cLabel, table, btn, btnLabel1);
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
        Text flLabel = new Text("Following");
        flLabel.setStyle("-fx-font: 24 arial;");

        Text lable = new Text("Received Msgs");
        //send button
        Button sendButton = new Button("Send");
        //refresh button
        Button refreshButton = new Button("Refresh");
        refreshButton.setVisible(false);
        //del button
        Button delButton = new Button("Delete All");

        //creating Hbox for buttons paralel to each other
        HBox buttons = new HBox();
        buttons.getChildren().addAll(sendButton,delButton, refreshButton);
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
        ResultSet userFriends = dbobj.getFollowers(userName);
        while (userFriends.next()) {
            friendsList.getItems().add(
                    userFriends.getString("followingName")//adding users in drop down from database
            );

        }
        friendsList.setPrefHeight(140);
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
                refreshButton.fire();//calling refresh button to auto refresh msgs after sending

            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //action listner of refresh button  
        refreshButton.setOnAction((javafx.event.ActionEvent e) -> {
            //setting text in msg box for recieved msg
            ResultSet msgs = null;
            ResultSet msgs1 = null;
            try {
                msgs = dbobj.getMsg(userName);
                msgs1 = dbobj.getMsg(userName);//second Resultset for checking if theres no msg
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

            //for empty msgs
            try {
                if (msgs1.next() == false) {
                    textArea.setText("");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            String setText = "";

            try {
                while (msgs.next()) {
                    //setting msgs into the text box
                    String from = msgs.getString("msgSender");
                    String msgContent = msgs.getString("msgContent");

                    setText = setText + "From " + from + ":\n" + msgContent + "\n";
                    textArea.setText(setText);

                }
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        //adding listener to delete btn
        delButton.setOnAction((javafx.event.ActionEvent e) -> {
            try {

                dbobj.deleteMsgs(userName);

                refreshButton.fire();//calling refresh button listener after deleting msgs

            } catch (SQLException ex) {
                System.out.println("3");
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
        search.setEditable(true);
        ResultSet searchUsers = dbobj.getUsers();
        while (searchUsers.next()) {
            search.getItems().addAll(
                    searchUsers.getString("userName")
            );
        }

        btnViewFollower.setOnAction((javafx.event.ActionEvent e) -> {
            //DBUtility db = new DBUtility();
            try {
                db.dbConnect();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {

                String friendName = friendsList.getSelectionModel().getSelectedItems().toString();
                friendName = friendName.substring(1, friendName.length() - 1);
                User user = new User(db.getUserInfo(friendName));
                user.getDashboard().friendDashboard(this.userName, this.userID);
                db.dbClose();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        btnViewFollowing.setOnAction((javafx.event.ActionEvent e) -> {
            //DBUtility db = new DBUtility();
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
                user.getDashboard().friendDashboard(this.userName, this.userID);
                db.dbClose();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        HBox searchBox = new HBox(search, btnViewFollowing);
        searchBox.setSpacing(20);
        //set search bar default value
        search.setPromptText("Username");
        rightVbox.getChildren().addAll(searchBox, flLabel, friendsList, btnViewFollower, friends1, lable, scrollPane, msgType, buttons);

        //set up bottom pane
        Text bottomText = new Text("Created by Keaton, Will, Mike, and Amin (2019)");

        btnAddGameUser.setOnAction((ActionEvent e) -> {

            try {
                addGameUserList();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        //create border pane with each part as set up above
        BorderPane bPane = new BorderPane();
        bPane.setRight(rightVbox);
        bPane.setBottom(bottomText);
        bPane.setLeft(leftVbox);

        //width, height of actual 
        Scene dashboard = new Scene(bPane, 1100, 650);

        dashboard.getStylesheets().add(CapstoneWeek1.class.getResource("SocialGamerStyle.css").toExternalForm());

        dashboardStage.setScene(dashboard);
        dashboardStage.setMinHeight(650);
        dashboardStage.setMinWidth(1350);

        //primaryStage.close();
        dashboardStage.show();
        //set background color to a light grey
        bPane.setStyle("-fx-background-color: #DCDCDC;");
         Thread thread = new Thread(){
                                    @Override
				    public void run(){
				    	
				    	try {
					         for(int i = 1; ; i++) {
                                                    refreshButton.fire();//calling refresh button to auto refresh msgs after sending
					            Thread.sleep(1000);
					         }
					      }catch (InterruptedException e) {
					         System.out.println("Thread  interrupted.");
					      }
				    }
				    
				  };
				  thread.start();


    }

    public void addGameDashboard() {

        ArrayList<String> genreList = new ArrayList<String>();
        genreList.add("Fighting");
        genreList.add("Racing");
        genreList.add("Sports");
        genreList.add("First Person Shooter (FPS)");
        genreList.add("Real Time Strategy (RTS)");
        genreList.add("Role Playing Game (RPG)");
        genreList.add("Multiplayer Online Battle Arena (MOBA)");
        genreList.add("Adventure");
        genreList.add("Horror");
        Collections.reverse(genreList);

        CheckBox cbPS1 = new CheckBox("Playstation");
        CheckBox cbPS2 = new CheckBox("Playstation 2");
        CheckBox cbPS3 = new CheckBox("Playstation 3");
        CheckBox cbPS4 = new CheckBox("Playstation 4");
        CheckBox cbXB = new CheckBox("Xbox");
        CheckBox cbXB360 = new CheckBox("Xbox 360");
        CheckBox cbXB1 = new CheckBox("Xbox One");
        CheckBox cbNES = new CheckBox("Nintendo");
        CheckBox cbSNES = new CheckBox("Super Nintendo");
        CheckBox cbN64 = new CheckBox("Nintendo 64");
        CheckBox cbGC = new CheckBox("Game Cube");
        CheckBox cbWii = new CheckBox("Wii");
        CheckBox cbWiiU = new CheckBox("Wii U");
        CheckBox cbSwitch = new CheckBox("Nintendo Switch");
        CheckBox cbPC = new CheckBox("PC");

        VBox vboxPlatform = new VBox();
        vboxPlatform.getChildren().addAll(cbPS1, cbPS2, cbPS3, cbPS4, cbXB, cbXB360, cbXB1, cbNES, cbSNES, cbN64, cbGC, cbWii, cbWiiU, cbSwitch, cbPC);

        ArrayList<Integer> yearsList = new ArrayList<Integer>();
        for (int i = 1970; i < 2020; i++) {
            yearsList.add(i);
        }
        Collections.reverse(yearsList);

        VBox addGameVbox = new VBox();
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label lbGameTitle = new Label("Game Title");
        TextField txtGameTitle = new TextField();

        Label lbYearPublished = new Label("Year Published");
        ComboBox cBoxYearPublished = new ComboBox();
        yearsList.forEach((x) -> cBoxYearPublished.getItems().add(x));

        Label lbGenre = new Label("Genre");
        ComboBox cBoxGenre = new ComboBox();
        genreList.forEach((x) -> cBoxGenre.getItems().add(x));

        Label lbPlatform = new Label("Please select all platforms");

        grid.add(lbGameTitle, 0, 0);
        grid.add(txtGameTitle, 1, 0);
        grid.add(lbYearPublished, 0, 1);
        grid.add(cBoxYearPublished, 1, 1);
        grid.add(lbGenre, 0, 2);
        grid.add(cBoxGenre, 1, 2);
        grid.add(lbPlatform, 0, 3);
        grid.add(vboxPlatform, 0, 4);

//        addGameVbox.getChildren().addAll(lbGameTitle, txtGameTitle, lbYearPublished, cBoxYearPublished, lbGenre, cBoxGenre, lbPlatform);
        Button btnAddGame = new Button("Add Game");

        VBox sceneVBox = new VBox();
        sceneVBox.getChildren().addAll(grid, btnAddGame);
        sceneVBox.setAlignment(Pos.CENTER);

        Stage addGameStage = new Stage();
        addGameStage.setTitle("Add a new game to the Library");

        Scene addGameDashboard = new Scene(sceneVBox);

        addGameStage.setScene(addGameDashboard);
//        addGameStage.setMinHeight(450);
//        addGameStage.setMinWidth(550);

        addGameStage.show();

        btnAddGame.setOnAction((ActionEvent e) -> {

            String gameTitle = txtGameTitle.getText();
            int yearPublished = (int) cBoxYearPublished.getValue();
            String genre = (String) cBoxGenre.getValue();

            DBUtility db = new DBUtility();
            try {
                db.dbConnect();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                db.addGame(gameTitle, yearPublished, genre);
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                db.dbClose();
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Game Added!");
            alert.setHeaderText(null);
            alert.setContentText("Game Added to the library!");
            alert.showAndWait();

            addGameStage.close();

        });

    }

    public void addGameUserList() throws SQLException {

        ListView gamesList = new ListView();
        DBUtility db = new DBUtility();
        db.dbConnect();
        ResultSet gamesListResults = db.getGamesLibrary();
        while (gamesListResults.next()) {
            gamesList.getItems().add(
                    gamesListResults.getString("Title")//adding users in drop down from database
            );

        }

        VBox sceneVBox = new VBox();
        Button btnAddGame = new Button("Add to my list");
        sceneVBox.getChildren().addAll(gamesList, btnAddGame);
        sceneVBox.setAlignment(Pos.CENTER);
        sceneVBox.setPadding(new Insets(20, 20, 20, 20));
        sceneVBox.setSpacing(20.0);

        Stage addGameStage = new Stage();
        addGameStage.setTitle("Add a new game to your List");

        Scene addGameDashboard = new Scene(sceneVBox);

        addGameStage.setScene(addGameDashboard);

        addGameStage.show();

        btnAddGame.setOnAction((javafx.event.ActionEvent e) -> {
            try {

                String gameTitle = gamesList.getSelectionModel().getSelectedItems().toString();
                gameTitle = gameTitle.substring(1, gameTitle.length() - 1);
                db.addGameToUserList(gameTitle, this.userID);

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Game Added to Your List!");
                alert.setHeaderText(null);
                alert.setContentText("Game Added to Your List!");
                alert.showAndWait();

                addGameStage.close();

                gamesTable.setItems(db.getGamesPlayed(this.userID));
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    public void friendDashboard(String rootUserName, int rootUserID) throws SQLException {

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

        //FollowButton
        Button btnFollow = new Button("Follow");

        homeButton.setGraphic(homeView);
        //vbox for holding name over current game
        VBox nameAndGame = new VBox();
        Text name = new Text(fName + " " + lName);
        name.setStyle("-fx-font: 24 arial;");
        Text currentGame = new Text("Now Playing: Call of Duty: Modern Warfare 2");
        currentGame.setStyle("fx-font: 16 arial;");
        nameAndGame.getChildren().addAll(name, currentGame, btnFollow);
        //flow pane for holding picture, name/game, and messages button
        FlowPane topPane = new FlowPane();
        Text tab = new Text("\t   ");
        //add to top pane
        topPane.getChildren().addAll(tab, profilePicView, nameAndGame);
        topPane.setHgap(10);
        nameAndGame.setAlignment(Pos.CENTER_LEFT);

        Image tableExample = new Image("sampleTable.png");
        ImageView tableView = new ImageView(tableExample);

        gamesTable.setEditable(true);

        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setMinWidth(250.0);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("gameTitle"));
        TableColumn yearColumn = new TableColumn("Release Date");
        yearColumn.setMinWidth(150.0);
        yearColumn.setCellValueFactory(
                new PropertyValueFactory<>("year"));
        TableColumn genreColumn = new TableColumn("Genre");
        genreColumn.setMinWidth(250.0);
        genreColumn.setCellValueFactory(
                new PropertyValueFactory<>("genre"));

        DBUtility db = new DBUtility();

        db.dbConnect();
        gamesTable.setItems(db.getGamesPlayed(rootUserID));
        gamesTable.getColumns().addAll(titleColumn, yearColumn, genreColumn);

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
        table.getChildren().addAll(tab2, gamesTable);
        Text tab3 = new Text("\t   ");
        HBox btn = new HBox();
        btn.getChildren().addAll(tab3);
        Separator horizSep = new Separator();
        horizSep.setOrientation(Orientation.HORIZONTAL);
        leftVbox.getChildren().addAll(topPane, bioLabel, cLabel, table, btn, btnLabel1);
        leftVbox.setAlignment(Pos.TOP_LEFT);
        leftVbox.setSpacing(10);

        //set up bottom pane
        Text bottomText = new Text("Created by Keaton, Will, Mike, and Amin (2019)");

        //Checking to see if user is following clicked page
        //If they are already following, then the follow button will say "Following"
        //DBUtility db = new DBUtility();
        try {
            db.dbConnect();
            ResultSet resultSet = db.getFollowers(rootUserName);
            while (resultSet.next()) {
                System.out.println("UserName check: " + rootUserName);
                System.out.println(resultSet.getString("FollowingName"));
                String nameCheck = resultSet.getString("FollowingName");
                if (nameCheck.equals(this.userName)) {
                    btnFollow.setText("Following");
                }

            }
            db.dbClose();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Follow button will insert follower into Followers Table if the button text equals "Follow"
        btnFollow.setOnAction((javafx.event.ActionEvent e) -> {
            //DBUtility db = new DBUtility();
            if (btnFollow.getText().equals("Follow")) {
                try {
                    db.dbConnect();
                    db.addFollow(rootUserName, this.userName, rootUserID, this.userID);
                    btnFollow.setText("Following");

                } catch (SQLException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        //create border pane with each part as set up above
        BorderPane bPane = new BorderPane();
        // bPane.setRight(rightVbox);
        bPane.setBottom(bottomText);
        bPane.setLeft(leftVbox);

        //width, height of actual scene
        Scene friendDashboard = new Scene(bPane, 1100, 650);

        dashboardStage.setScene(friendDashboard);
        dashboardStage.setMinHeight(450);
        dashboardStage.setMinWidth(550);

        //primaryStage.close();
        dashboardStage.show();
        //set background color to a light grey
        bPane.setStyle("-fx-background-color: #DCDCDC;");

    }

}
