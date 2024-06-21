package books;

public class HistoryBook extends Book{
    private String category;

    public HistoryBook(){

    }
    public HistoryBook(String category){
        super(category);
        this.category = category;

    }


    @Override
    public void setCategory(String category){
        this.category = category;
    }



    @Override
    public String getCategory(){
        return category;
    }
    @Override
    public String toString(){
        return category;
    }
}
