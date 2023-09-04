// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import  db.DatabaseManager;
import book.BookService;
import  book.book;

import java.awt.print.Book;
import  java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
//        System.out.println("entere the ISBN  delete the book ");
//        String isbn=scanner.nextLine();
        BookService book=new BookService();
//       book book1=  book.getBookByISBN("b1");
        System.out.println(book.getAllBooks());
//       int res= book.updateBook("2bb","ss","ss","ss",44);

//        System.out.println("res:"+res);
//        if (res) {
//            System.out.println("Book updated successfully.");
//        } else {
//            System.out.println("Book not found or update failed.");
//        }
//    book.createBook("33sbn","ayoub","ss","ss",4);

    }
}