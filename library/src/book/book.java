package book;

public class book {

    private  String ISBN;
    private  String title;
    private  String author;
    private  String informations;
    private  int quantity;

    public  String getISBN(){
      return   this.ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getInformations() {
        return informations;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public book(String isbn, String title, String author, String informations, int quantity) {
        ISBN = isbn;
        this.title = title;
        this.author = author;
        this.informations = informations;
        this.quantity = quantity;
    }

    public book(){}

    public String toString() {
        return "\n===============================================" +
                "\nISBN: " + ISBN +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nInformation: " + informations +
                "\nQuantity: " + quantity +
                "\n";
    }

}
