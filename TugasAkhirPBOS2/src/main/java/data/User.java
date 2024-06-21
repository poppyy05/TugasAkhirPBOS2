package data;

import data.Student;
import books.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javafx.stage.Stage;
import data.SendEmail;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;


public class User {

    Image backgroundImage = new Image("file:src/main/java/img/Library.jpg");

    BackgroundFill backgroundFill = new BackgroundFill(
            new ImagePattern(backgroundImage),
            CornerRadii.EMPTY,
            Insets.EMPTY
    );
    Background background = new Background(backgroundFill);


    //Method yang digunakan untuk meminjam buku
    public void choiceBooks() {


        Book bookObj = new Book();
        Student studentObj = new Student();

        Stage choiceBooksStage = new Stage();
        choiceBooksStage.setTitle("Choose Book");



        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> idColumn = new TableColumn<>("Book ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Name");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(authorColumn);
        tableView.getColumns().add(categoryColumn);
        tableView.getColumns().add(stockColumn);

        for (Book i : Book.arr_bookList) {
            tableView.getItems().add(i);
            tableView.refresh();
        }


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(tableView,0,0);

        Label bookIdLabel = new Label("Input Book ID what want to borrow:");
        grid.add(bookIdLabel,  0, 1);

        TextField bookIdField = new TextField();
        grid.add(bookIdField, 0, 2);

        Label durationLabel = new Label("How much days to Borrow Books? (Max 14 days)");

        TextField durationField = new TextField();
        durationField.setPromptText("How much days?");
        Label messageLabel = new Label();

        grid.add(durationLabel,0,3);
        grid.add(durationField, 0,4);
        grid.add(submitButton,0,5);
        grid.add(backBtn, 3, 5);
        grid.add(messageLabel, 0, 6);
        grid.setBackground(background);

        Scene scene = new Scene(grid, 1080, 720);
        choiceBooksStage.setScene(scene);
        choiceBooksStage.show();

        backBtn.setOnAction(event -> {
            studentObj.menu();
            choiceBooksStage.close();
        });

        submitButton.setOnAction(e -> {
            boolean validasi = false;

            String idBukuYangDipinjam = bookIdField.getText();
            String bookTitle = "";
            int duration = 0;

            for (Book book : Book.arr_bookList) {
                if (book.getBookId().equals(idBukuYangDipinjam)) {
                    if (book.getStock() > 0) {
                        duration = Integer.parseInt(durationField.getText());
                        try {
                            if(duration != 0 && duration <= 14) {
                                int newStock = book.getStock() - 1;
                                book.setStock(newStock);
                                bookTitle = book.getTitle();

                                // Get the currently logged-in student's NIM
                                String currentStudentNim = SessionMethod.getLoggedInStudent().getNim();

                                // Create a new Book object for the borrowed book
                                Book borrowedBook = new Book(
                                        book.getBookId(),
                                        book.getTitle(),
                                        book.getAuthor(),
                                        book.getCategory(),
                                        1,  // Quantity borrowed is always 1
                                        duration,
                                        currentStudentNim
                                );

                                Book.arr_borrowedBook.add(borrowedBook);

                                validasi = true;
                                break;
                            } else {
                                messageLabel.setText("Max 14 days");
                            }
                        } catch (NumberFormatException ex) {
                            messageLabel.setText("Duration must be a number");
                        }
                    } else {
                        messageLabel.setText("== Stock Empty! ==");
                        return;
                    }
                }
            }

            if(validasi) {
                SendEmail sndobj = new SendEmail();
                String recipient = "malthoft@gmail.com";
                String subject = "Book Borrowed Notification";
                String body = "You have successfully borrowed the book: " + bookTitle;
                sndobj.sendEmailNotification(recipient, bookTitle, subject, body);
                messageLabel.setText("==== Book Successfully Borrowed! ====");
                tableView.getItems().clear();
                tableView.getItems().addAll(Book.arr_bookList);
            } else {
                messageLabel.setText("== ID book not found! ==");
            }
        });
    }


    public void inputBook() {
        Book  textBookObj    = new TextBook();
        Book  storyBookObj   = new StoryBook();
        Book  historyBookObj = new HistoryBook();

        Stage inputBookStage = new Stage();
        inputBookStage.setTitle("Input Book");

        //Label
        Label sceneTitle = new Label("Add Book");
        sceneTitle.setMaxWidth(Double.MAX_VALUE);
        Label bookIdLabel    = new Label("Book ID :");
        bookIdLabel.setMaxWidth(Double.MAX_VALUE);
        Label bookTitleLabel = new Label("Book Title :");
        bookTitleLabel.setMaxWidth(Double.MAX_VALUE);
        Label authorLabel    = new Label("Author :");
        authorLabel.setMaxWidth(Double.MAX_VALUE);
        Label stockLabel     = new Label("Stock :");
        stockLabel.setMaxWidth(Double.MAX_VALUE);
        Label categoryLabel = new Label("Category :");
        categoryLabel.setMaxWidth(Double.MAX_VALUE);

        //Font Label style
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        bookIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        bookTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        authorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        stockLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Label errorMessageLabel = new Label("There is something Wrong!");

        TextField bookIdField    = new TextField();
        TextField bookTitleField =  new TextField();
        TextField authorField    = new TextField();
        TextField stockField     = new TextField();
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("History", "Story", "Text");
        categoryComboBox.setPromptText("Select category");
        categoryComboBox.setMaxWidth(Double.MAX_VALUE);
        
        //Font label color
        sceneTitle.setStyle("-fx-text-fill: #FF0080;");

        errorMessageLabel.setStyle("-fx-text-fill: #FF1E1E;");

        //Font visible settings
        errorMessageLabel.setVisible(false);

        //Button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #FFDA78; -fx-text-fill: white; -fx-font-weight: bold;");


        //Grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.add(sceneTitle, 0,0);
        grid.add(bookIdLabel, 0,1);
        grid.add(bookTitleLabel,0,2);
        grid.add(authorLabel, 0,3);
        grid.add(stockLabel, 0,4);
        grid.add(categoryLabel, 0, 5);
        grid.add(errorMessageLabel, 0, 7);

        grid.add(bookIdField,2,1);
        grid.add(bookTitleField, 2,2);
        grid.add(authorField, 2,3);
        grid.add(stockField,2,4);
        grid.add(categoryComboBox, 2, 5);

        grid.add(submitButton,0,6);
        grid.add(backBtn, 3, 6);

        grid.setVgap(10);
        grid.setHgap(5);
        grid.setBackground(background);


        Scene scene = new Scene(grid, 1280, 720);
        inputBookStage.setScene(scene);
        inputBookStage.show();


        //Action Button
        backBtn.setOnAction(event -> {
            Admin adminObj = new Admin();
            adminObj.menu();
            inputBookStage.close();
        });

        submitButton.setOnAction(event -> {
            try {
                Admin adminObj = new Admin();
                Book bookObj = new Book();
                errorMessageLabel.setVisible(false);

                bookObj.setBookId(bookIdField.getText());
                bookObj.setTitle(bookTitleField.getText());
                bookObj.setAuthor(authorField.getText());
                bookObj.setStock(Integer.parseInt(stockField.getText()));

                String selectedCategory = categoryComboBox.getValue(); // Get the selected category

                if (selectedCategory != null) {
                    if (selectedCategory.equalsIgnoreCase("History")) {
                        historyBookObj.setCategory("History Book");
                        Book.arr_bookList.add(new Book(bookObj.getBookId(), bookObj.getTitle(), bookObj.getAuthor(), historyBookObj.getCategory(), bookObj.getStock()));
                        adminObj.menu();
                        inputBookStage.close();
                    } else if (selectedCategory.equalsIgnoreCase("Story")) {
                        storyBookObj.setCategory("Story Book");
                        Book.arr_bookList.add(new Book(bookObj.getBookId(), bookObj.getTitle(), bookObj.getAuthor(), storyBookObj.getCategory(), bookObj.getStock()));
                        adminObj.menu();
                        inputBookStage.close();
                    } else if (selectedCategory.equalsIgnoreCase("Text")) {
                        textBookObj.setCategory("Text Book");
                        Book.arr_bookList.add(new Book(bookObj.getBookId(), bookObj.getTitle(), bookObj.getAuthor(), textBookObj.getCategory(), bookObj.getStock()));
                        adminObj.menu();
                        inputBookStage.close();
                    }
                } else {
                    errorMessageLabel.setVisible(true);
                    errorMessageLabel.setText("Please select a category");
                }
            } catch (NumberFormatException message) {
                errorMessageLabel.setVisible(true);
                inputBookStage.show();
            }
        });


    }

}


