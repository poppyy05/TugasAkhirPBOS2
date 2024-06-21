package com.main;

import books.Book;
import data.Admin;
import data.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;

import java.util.HashMap;
import java.util.Map;

import static data.Student.backgroundImage;

public class LibrarySystem extends Application {

    BackgroundFill backgroundFill = new BackgroundFill(
            new ImagePattern(backgroundImage),
            CornerRadii.EMPTY,
            Insets.EMPTY
    );
    Background background = new Background(backgroundFill);



    @Override
    public void start(Stage primaryStage) {

        showChoicePane(primaryStage);
        Student.arr_userStudent.add(new Student.UserStudent("althof", "123456789098765", "Teknik", "Infor"));
        Student.arr_userStudent.add(new Student.UserStudent("bangtoyib", "123456789876543", "Teknik", "Sipil"));

    }

    private void showChoicePane(Stage primaryStage) {
        GridPane choicePane = new GridPane();
        choicePane.setAlignment(Pos.CENTER);
        choicePane.setHgap(10);
        choicePane.setVgap(10);
        choicePane.setPadding(new Insets(25, 25, 25, 25));
        Image backgroundImage = new Image("file:src/main/java/img/Library.jpg");


        choicePane.setBackground(background);


        Text choiceTitle = new Text("Select Role");
        choiceTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        choiceTitle.setFill(Color.web("#333333"));
        choicePane.add(choiceTitle, 0, 0, 2, 1);

        Button adminBtn = new Button("Admin");
        adminBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        Button userBtn = new Button("User");
        userBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().addAll(adminBtn, userBtn);
        choicePane.add(hbBtn, 0, 1, 2, 1);

        Scene choiceScene = new Scene(choicePane, 1280, 720);

        primaryStage.setScene(choiceScene);
        primaryStage.setTitle("Select Role");
        primaryStage.show();

        adminBtn.setOnAction(e -> adminLoginForm(primaryStage));
        userBtn.setOnAction(e -> studentLoginForm(primaryStage));
    }

    private void adminLoginForm(Stage primaryStage) {
        Admin adminObj = new Admin();
        Label errorLoginMessage   = new Label("User Not Found!");
        errorLoginMessage.setVisible(false);
        errorLoginMessage.setStyle("-fx-text-fill: red");
        errorLoginMessage.setMaxWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(background);

        Text scenetitle = new Text("Login Admin");
        scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        scenetitle.setFill(Color.web("#333333"));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        userName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        userName.setTextFill(Color.web("#333333"));
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #cccccc;");
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        pw.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        pw.setTextFill(Color.web("#333333"));
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        pwBox.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #cccccc;");
        grid.add(pwBox, 1, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(loginBtn, backBtn);
        grid.add(hbBtn, 1, 4);

        grid.add(errorLoginMessage, 1, 5);

        final Text actiontarget = new Text();
        actiontarget.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        grid.add(actiontarget, 1, 6);

        loginBtn.setOnAction(e -> {
            if(userTextField.getText().equals(Admin.adminusername) && pwBox.getText().equals(Admin.adminpassword)) {

                adminObj.menu();
                primaryStage.close();
            }else {
                errorLoginMessage.setVisible(true);
            }
        });

        backBtn.setOnAction(e -> showChoicePane(primaryStage));



        Scene scene = new Scene(grid, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Admin");
    }

    private void studentLoginForm(Stage primaryStage) {
        Student studentObj = new Student();
        Label errorLoginMessage   = new Label("User not found !");
        errorLoginMessage.setVisible(false);
        errorLoginMessage.setStyle("-fx-text-fill: red");
        errorLoginMessage.setMaxWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(background);

        Text scenetitle = new Text("Login User");
        scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        scenetitle.setFill(Color.web("#333333"));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("NIM:");
        userName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        userName.setTextFill(Color.web("#333333"));
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #cccccc;");
        grid.add(userTextField, 1, 1);


        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(loginBtn, backBtn);
        grid.add(hbBtn, 1, 4);

        grid.add(errorLoginMessage, 1, 5);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        loginBtn.setOnAction(e -> {
            if(userTextField.getText().length() == 15) {

                if (studentObj.isStudents(userTextField)) {
                    Book.arr_bookList.add(new Book("1","Solo Leveling","org jepang","Story Book",8));
                    Book.arr_bookList.add(new Book("2","Maling Kundang","org indo","History BOok",3));
                    errorLoginMessage.setVisible(false);

                    primaryStage.close();

                } else {
                    errorLoginMessage.setVisible(true);
                }
            }
        });

        backBtn.setOnAction(e -> showChoicePane(primaryStage));



        Scene scene = new Scene(grid, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login User");
    }


    public static void main(String[] args) {
        launch(args);
    }
}