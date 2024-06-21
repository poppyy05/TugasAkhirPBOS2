package books;
public class TextBook extends Book{
    private String category;

    public TextBook(){

    }
    public TextBook(String category){
        super(category);
        this.category = category;

    }

    @Override
    public void setCategory(String category) {
        this.category = category;

    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public  String toString(){
        return category;
    }

}