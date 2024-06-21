package books;

import java.util.ArrayList;

public class Book {


    private String bookId;
    private String title;
    private String author;
    private String category;
    private int stock;
    private int duration;
    private String borrowerNim;

    public static ArrayList<Book> arr_bookList = new ArrayList<>();


    public static ArrayList<Book> arr_borrowedBook = new ArrayList<>();


    public Book(){

    }

    public Book(String category){
        this.category = category;
    }
    public Book(String bookId, int stock, int duration){
        this.bookId   = bookId;
        this.stock    = stock;
        this.duration = duration;

    }
    public Book(String bookId, String title, String author,String category, int stock){
        this.bookId   = bookId;
        this.title    = title;
        this.author   = author;
        this.category = category;
        this.stock    = stock;

    }

    public Book(String bookId, String title, String author,String category, int stock, int duration, String borrowerNim){
        this.bookId   = bookId;
        this.title    = title;
        this.author   = author;
        this.category = category;
        this.stock    = stock;
        this.duration = duration;
        this.borrowerNim = borrowerNim;

    }
    public Book copy() {
        return new Book(this.bookId, this.title, this.author, this.category, this.stock, this.duration, this.borrowerNim);
    }



    public void setBookId(String bookId){
        this.bookId     = bookId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }



    public String getBookId(){
        return bookId;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getCategory(){
        return category;
    }
    public int getStock(){
        return stock;
    }
    public int getDuration(){
        return duration;
    }
    public String getBorrowerNim() {
        return borrowerNim;
    }

    public void setBorrowerNim(String borrowerNim) {
        this.borrowerNim = borrowerNim;
    }


}
