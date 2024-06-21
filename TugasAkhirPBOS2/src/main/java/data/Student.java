package data;


import books.Book;
import com.main.LibrarySystem;
import javafx.geometry.Insets;
import util.iMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;


import java.util.ArrayList;

public class Student extends User implements iMenu {
    public static ArrayList<UserStudent> arr_userStudent = new ArrayList<>();

    private String nama;
    private String nim;
    private String fakultas;
    private String prodi;

    public String getNim() {
        return this.nim;
    }

    public Student(String nama, String nim, String fakultas, String prodi) {
        this.nama = nama;
        this.nim = nim;
        this.fakultas = fakultas;
        this.prodi = prodi;
    }

    public Student() {
    }

    //Konstruktor untuk arraylist arr_userStudent.
    public static class UserStudent {
        String nama, nim, fakultas, prodi;

        public UserStudent(String nama, String nim, String fakultas, String prodi) {
            this.nama = nama;
            this.nim = nim;
            this.fakultas = fakultas;
            this.prodi = prodi;
        }
    }

    public static Image backgroundImage = new Image("file:src/main/java/img/Library.jpg");

    static BackgroundFill backgroundFill = new BackgroundFill(
            new ImagePattern(backgroundImage),
            CornerRadii.EMPTY,
            Insets.EMPTY
    );
    static Background background = new Background(backgroundFill);

    @Override
    public void menu() {
        Stage studentMenuStage = new Stage();
        studentMenuStage.setTitle("Student Menu");

        //Label

        Label sceneTitle = new Label("Welcome, " + SessionMethod.getLoggedInStudent().nama);
        Label nimLabel = new Label("NIM: " + SessionMethod.getLoggedInStudent().nim);
        Label fakultasLabel = new Label("Faculty: " + SessionMethod.getLoggedInStudent().fakultas);
        Label prodiLabel = new Label("Program: " + SessionMethod.getLoggedInStudent().prodi);

        //Font style
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        //Font color
        sceneTitle.setStyle("-fx-text-fill: #A91D3A;");

        //Button
        Button borrowedBookButton = new Button("Borrowed Books");
        borrowedBookButton.setMaxWidth(Double.MAX_VALUE);
        borrowedBookButton.setStyle("-fx-background-color: #3ABEF9; -fx-text-fill: white; -fx-font-weight: bold;");
        Button borrowBookButton = new Button("Borrow and display Book");
        borrowBookButton.setStyle("-fx-background-color: #8576FF; -fx-text-fill: white; -fx-font-weight: bold;");
        borrowBookButton.setMaxWidth(Double.MAX_VALUE);
        Button returnBookButton = new Button("Return Book");
        returnBookButton.setMaxWidth(Double.MAX_VALUE);
        returnBookButton.setStyle("-fx-background-color: #68D2E8; -fx-text-fill: white; -fx-font-weight: bold;");
        Button logoutButton = new Button("Logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setStyle("-fx-background-color: #EE4E4E; -fx-text-fill: white; -fx-font-weight: bold;");


        //Grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.add(sceneTitle, 1, 0);

        grid.add(borrowedBookButton, 1, 1);
        grid.add(borrowBookButton, 1, 2);
        grid.add(returnBookButton, 1, 3);
        grid.add(logoutButton, 1, 7);

        grid.setVgap(10);
        grid.setHgap(5);
        grid.setBackground(background);

        Scene studentmenuScene = new Scene(grid, 1280, 720);
        studentMenuStage.setScene(studentmenuScene);
        studentMenuStage.show();

        //Action button
        borrowedBookButton.setOnAction(event -> {
            showBorrowedBooks();
            studentMenuStage.close();
        });

        borrowBookButton.setOnAction(event -> {
            choiceBooks();
            studentMenuStage.close();
        });

        returnBookButton.setOnAction(event -> {
            returnBooks();
            studentMenuStage.close();
        });

        logoutButton.setOnAction(event -> {
            LibrarySystem librarySystemObj = new LibrarySystem();
            librarySystemObj.start(new Stage());
            studentMenuStage.close();
        });

    }

    @Override
    public void choiceBooks() {
        super.choiceBooks();
    }

    public static void showBorrowedBooks() {
        Stage showBorrowedBooksStage = new Stage();
        showBorrowedBooksStage.setTitle("List of Borrowed Book");

        TableView<Book> table = new TableView<>();
        table.setPrefSize(700, 500);

        TableColumn<Book, String> idColumn = new TableColumn<>("Book ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Name");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> durationColumn = new TableColumn<>("Durasi");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        table.getColumns().add(idColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(authorColumn);
        table.getColumns().add(categoryColumn);
        table.getColumns().add(durationColumn);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        String currentStudentNim = SessionMethod.getLoggedInStudent().nim;
        for (Book borrowedBook : Book.arr_borrowedBook) {
            if (borrowedBook.getBorrowerNim().equals(currentStudentNim)) {
                for (Book book : Book.arr_bookList) {
                    if (book.getBookId().equals(borrowedBook.getBookId())) {
                        Book completeBook = new Book(
                                book.getBookId(),
                                book.getTitle(),
                                book.getAuthor(),
                                book.getCategory(),
                                book.getStock(),
                                borrowedBook.getDuration(),
                                currentStudentNim
                        );
                        table.getItems().add(completeBook);
                        break;
                    }
                }
            }
        }

        backBtn.setOnAction(event -> {
            Admin adminObj = new Admin();
            adminObj.menu();
            showBorrowedBooksStage.close();
        });


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.add(table, 0, 0);
        gridPane.add(backBtn, 3, 3);
        gridPane.setBackground(background);

        backBtn.setOnAction(event -> {
            Student studentObj = new Student();

            studentObj.menu();
            showBorrowedBooksStage.close();

        });


        Scene scene = new Scene(gridPane, 1280, 720);
        showBorrowedBooksStage.setScene(scene);
        showBorrowedBooksStage.show();

    }

    public static void returnBooks() {

        Stage returnBooksStage = new Stage();
        returnBooksStage.setTitle("Return Book");

        //Label
        Label headerTitle = new Label("Return Book");
        Label bookIdLabel = new Label("Input Book ID what you want return");

        //Notification Label
        Label submitSuccesLabel = new Label("Return Successfully!");
        Label submitFailedLabel = new Label("Return Fails!");

        //Font Style
        headerTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        bookIdLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));

        //Font Color
        headerTitle.setStyle("-fx-text-fill: #A91D3A;");
        submitSuccesLabel.setStyle("-fx-text-fill: #16FF00;");
        submitFailedLabel.setStyle("-fx-text-fill: #FF1E1E;");

        //Notification label settings
        submitSuccesLabel.setVisible(false);
        submitFailedLabel.setVisible(false);

        //Field
        TextField bookIdField = new TextField();


        //Button
        Button submitButton = new Button("Return Book");
        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        //Table label
        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> idBookColumn = new TableColumn<>("Book ID");
        TableColumn<Book, String> titleBookColumn = new TableColumn<>("Title");
        TableColumn<Book, String> authorBookColumn = new TableColumn<>("Author");
        TableColumn<Book, String> categoryBookColumn = new TableColumn<>("Category");
        TableColumn<Book, String> durationBookColumn = new TableColumn<>("Duration (days)");

        //Table fill
        idBookColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleBookColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorBookColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryBookColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        durationBookColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tableView.getColumns().addAll(idBookColumn, titleBookColumn, authorBookColumn, categoryBookColumn, durationBookColumn);

        for (Book borrowedBook : Book.arr_borrowedBook) {
            String bookId = borrowedBook.getBookId();
            for (Book book : Book.arr_bookList) {
                if (book.getBookId().equals(bookId)) {
                    Book completeBook = new Book(
                            book.getBookId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCategory(),
                            borrowedBook.getDuration()
                    );
                    tableView.getItems().add(completeBook);
                    break;
                }
            }
        }


        //Grid layout
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(6);

        grid.add(headerTitle, 2, 0);
        grid.add(tableView, 2, 1);

        grid.add(bookIdLabel, 1, 2);
        grid.add(bookIdField, 1, 3);

        grid.add(submitSuccesLabel, 5, 2);
        grid.add(submitFailedLabel, 5, 2);

        grid.add(submitButton, 4, 4);
        grid.add(backBtn, 4, 5);
        grid.setBackground(background);

        Scene returnBookScene = new Scene(grid, 1280, 720);
        returnBooksStage.setScene(returnBookScene);
        returnBooksStage.show();

        //Action button
        submitButton.setOnAction(event -> {

            boolean validasiReturnBooks = false;

            for (int i = 0; i < Book.arr_borrowedBook.size(); i++) {


                if (Book.arr_borrowedBook.get(i).getBookId().equals(bookIdField.getText())) {
                    for (Book book : Book.arr_bookList) {
                        if (book.getBookId().equals(bookIdField.getText())) {

                            int returnBook = book.getStock();
                            returnBook++;
                            book.setStock(returnBook);

                            Book.arr_borrowedBook.remove(i);

                            validasiReturnBooks = true;
                            break;
                        }
                    }
                    if (validasiReturnBooks) {
                        break;
                    }
                }
            }
            tableView.getItems().clear();
            for (Book borrowedBook : Book.arr_borrowedBook) {
                String bookId = borrowedBook.getBookId();
                for (Book book : Book.arr_bookList) {
                    if (book.getBookId().equals(bookId)) {
                        Book completeBook = new Book(
                                book.getBookId(),
                                book.getTitle(),
                                book.getAuthor(),
                                book.getCategory(),
                                borrowedBook.getDuration()
                        );
                        tableView.getItems().add(completeBook);
                        break;
                    }
                }
            }


            if (validasiReturnBooks) {
                submitSuccesLabel.setVisible(true);
                submitFailedLabel.setVisible(false);

            } else {
                submitFailedLabel.setVisible(true);
                submitSuccesLabel.setVisible(false);
                submitFailedLabel.setText("Book ID not found!");
            }
        });

        backBtn.setOnAction(event -> {
            Student studentObj = new Student();

            studentObj.menu();
            returnBooksStage.close();

        });


    }



    public boolean isStudents(TextField username) {
        for (UserStudent i : arr_userStudent) {
            if (i.nim.equals(username.getText())) {
                SessionMethod.setLoggedInStudent(new Student(i.nama, i.nim, i.fakultas, i.prodi));
                menu();
                return true;
            }
        }
        return false;
}
}

