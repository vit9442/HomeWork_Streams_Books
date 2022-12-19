public class Book {
   private int id;
    private String title;
    private  double price;
    private  int amount;
    private  String image_path;
    private  int author_id;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getImage_path() {
        return image_path;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
    // Метод убирает символы, недопустимые в названии файла
        public void clearTitle(){
        title = title.replaceAll(":|\\.|\"|\\?","");
    }

}
