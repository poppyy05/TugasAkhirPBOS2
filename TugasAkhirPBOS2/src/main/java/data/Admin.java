package data;

import books.Book;
import com.main.LibrarySystem;
import util.iMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class Admin extends User implements iMenu {

    public static String adminusername = "admin";
    public static String adminpassword = "admin";

    Image backgroundImage = new Image("file:src/main/java/img/Library.jpg");

    BackgroundFill backgroundFill = new BackgroundFill(
            new ImagePattern(backgroundImage),
            CornerRadii.EMPTY,
            Insets.EMPTY
    );
    Background background = new Background(backgroundFill);


    @Override
    public void menu(){
        Stage adminMenuStage = new Stage();
        adminMenuStage.setTitle("Admin Menu");


        Label sceneTitle = new Label("Admin Menu");

        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        sceneTitle.setStyle("-fx-text-fill: #D10363;");


        //Button
        Button addStudentButton = new Button("Add Student");
        addStudentButton.setMaxWidth(Double.MAX_VALUE);
        addStudentButton.setStyle("-fx-background-color: #3ABEF9; -fx-text-fill: white; -fx-font-weight: bold;");
        Button displayStudentButton = new Button("Student List");
        displayStudentButton.setMaxWidth(Double.MAX_VALUE);
        displayStudentButton.setStyle("-fx-background-color: #68D2E8; -fx-text-fill: white; -fx-font-weight: bold;");
        Button addBookButton = new Button("Add Book");
        addBookButton.setMaxWidth(Double.MAX_VALUE);
        addBookButton.setStyle("-fx-background-color: #8576FF; -fx-text-fill: white; -fx-font-weight: bold;");
        Button displayBook  = new Button("List Book");
        displayBook.setMaxWidth(Double.MAX_VALUE);
        displayBook.setStyle("-fx-background-color: #4793AF; -fx-text-fill: white; -fx-font-weight: bold;");
        Button logoutButton = new Button("Logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setStyle("-fx-background-color: #EE4E4E; -fx-text-fill: white; -fx-font-weight: bold;");

        //Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.add(sceneTitle,1,0, 2, 1);

        grid.add(addStudentButton, 1,2);
        grid.add(displayStudentButton, 1,3);
        grid.add(addBookButton, 1,4);
        grid.add(displayBook, 1, 5);
        grid.add(logoutButton,1,10);

        grid.setVgap(10);
        grid.setHgap(6);
        grid.setBackground(background);

        Scene scene = new Scene(grid, 1280, 720);

        adminMenuStage.setScene(scene);
        adminMenuStage.show();

        //Action Button
        addStudentButton.setOnAction(event -> {
            addstudent();
            adminMenuStage.close();
        });

        displayStudentButton.setOnAction(event -> {
            displaystudent();
            adminMenuStage.close();
        });

        addBookButton.setOnAction(event -> {
            inputBook();
            adminMenuStage.close();
        });

        displayBook.setOnAction(event -> {
            displayBook();
            adminMenuStage.close();
        });

        logoutButton.setOnAction(event -> {
            LibrarySystem librarySystemObj = new LibrarySystem();
            Stage primaryStage = new Stage();
            librarySystemObj.start(primaryStage);
            adminMenuStage.close();
        });

    }


    public void addstudent() {

        Stage addStudentStage = new Stage();
        addStudentStage.setTitle("Add Student Page");


        //Label
        Label sceneTitle    = new Label("Add Student");
        Label nameLabel     = new Label("Name");
        Label nimLabel      = new Label("NIM");
        Label fakultasLabel = new Label("Faculty");
        Label jurusanLabel  = new Label("Programs");

        //Notification Label
        Label notificationLabel = new Label();
        notificationLabel.setVisible(false);


        //Field
        TextField nameField     = new TextField();
        TextField nimField      = new TextField();
        TextField fakultasField = new TextField();
        TextField jurusanField  = new TextField();

        //Font Style
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        nimLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        fakultasLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        jurusanLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        //Font Color
        sceneTitle.setStyle("-fx-text-fill: #FF0080;");
        notificationLabel.setStyle("-fx-text-fill: #FF1E1E;");

        //Button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        //Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.add(sceneTitle, 0, 0, 2,1);

        grid.add(nameLabel, 0,1);
        grid.add(nimLabel, 0,2);
        grid.add(fakultasLabel, 0,3);
        grid.add(jurusanLabel, 0,4);

        grid.add(nameField, 1,1);
        grid.add(nimField, 1,2);
        grid.add(fakultasField, 1,3);
        grid.add(jurusanField, 1,4);

        grid.add(submitButton,1,5);
        grid.add(backBtn, 2, 5);

        grid.add(notificationLabel, 1,6);

        grid.setVgap(10);
        grid.setHgap(5);
        grid.setBackground(background);


        Scene scene = new Scene(grid, 1280, 720);
        addStudentStage.setScene(scene);
        addStudentStage.show();

        //Action Button
        submitButton.setOnAction(event -> {
            String nim = nimField.getText();
            if (nim.length() == 15) {
                // Check if a student with this NIM already exists
                boolean nimExists = Student.arr_userStudent.stream()
                        .anyMatch(student -> student.nim.equals(nim));

                if (nimExists) {
                    notificationLabel.setText("A student with this NIM already exists!");
                    notificationLabel.setVisible(true);
                } else {
                    Admin adminObj = new Admin();
                    Student.arr_userStudent.add(new Student.UserStudent(
                            nameField.getText(),
                            nim,
                            fakultasField.getText(),
                            jurusanField.getText()
                    ));
                    adminObj.menu();
                }
            } else {
                notificationLabel.setText("NIM must be 15 digits!");
                notificationLabel.setVisible(true);
            }
        });

        backBtn.setOnAction(event -> {
            Admin adminObj = new Admin();
            adminObj.menu();
            addStudentStage.close();
        });



    }

    public void displaystudent() {
        // Membuat stage baru
        Stage displayStudentStage = new Stage();
        displayStudentStage.setTitle("List of Student");

        //Label
        Label sceneTitle    = new Label("List of Student");

        //Font Style
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Font Color
        sceneTitle.setStyle("-fx-text-fill: #FF0080;");

        // Membuat ListView untuk menampilkan mahasiswa
        ListView<String> listView = new ListView<>();
        listView.setPrefSize(700, 500);

        for (Student.UserStudent i : Student.arr_userStudent) {
            String studentInfo = "Name     : " + i.nama +"\n" +
                    "NIM      : " + i.nim + "\n" +
                    "Faculty : " + i.fakultas + "\n" +
                    "Programs    : " + i.prodi + "\n" +
                    "===========================";
            listView.getItems().add(studentInfo);
        }

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        backBtn.setOnAction(event -> {
            Admin adminObj = new Admin();
            adminObj.menu();
            displayStudentStage.close();
        });
        //Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.add(sceneTitle,0,0,2,1);
        grid.add(listView,0,1);
        grid.add(backBtn, 2, 5);


        grid.setVgap(5);
        grid.setBackground(background);

        Scene scene = new Scene(grid, 1280, 720);
        displayStudentStage.setScene(scene);
        displayStudentStage.show();
    }


    public void inputBook(){
        super.inputBook();
    }



    public void displayBook(){
        Stage showBooksStage = new Stage();
        showBooksStage.setTitle("List Book");

        Label sceneTitle    = new Label("List Book");

        //Font Style
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Font Color
        sceneTitle.setStyle("-fx-text-fill: #FF0080;");

        TableView<Book> table = new TableView<>();
        table.setPrefSize(700, 500);

        TableColumn<Book, String> idColumn = new TableColumn<>("ID Book");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Name Book");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().add(idColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(authorColumn);
        table.getColumns().add(categoryColumn);
        table.getColumns().add(stockColumn);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        for (Book book : Book.arr_bookList) {
            table.getItems().add(book);
        }

        backBtn.setOnAction(event -> {
            Admin adminObj = new Admin();
            adminObj.menu();
            showBooksStage.close();
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.add(sceneTitle,0,0, 2, 1);
        gridPane.add(table, 0, 1);
        gridPane.add(backBtn, 2, 6);
        gridPane.setBackground(background);

        Scene scene = new Scene(gridPane,1280,720);
        showBooksStage.setScene(scene);
        showBooksStage.show();
    }

}