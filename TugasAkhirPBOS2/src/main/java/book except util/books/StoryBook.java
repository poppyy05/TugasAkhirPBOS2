package books;
public class StoryBook extends Book {
    private String category;

    public StoryBook(){

    }
    public StoryBook(String category) {
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
    public String toString() {
        return category;
    }

}
