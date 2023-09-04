// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import  db.DatabaseManager;
import book.BookService;
import  book.book;
import copies.copiesService;
import copies.copies;
import java.awt.print.Book;

import java.sql.SQLException;
import  java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        int choice;
        Scanner scanner=new Scanner(System.in);


        do {
            runApp();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice){
                case 1:{
                    break;
                }
                case 2:{

                }
                case 3:{
                    System.out.println("can you put the ISBN of the book");

                    scanner.nextLine();
                   String ISBN= scanner.nextLine();
                   book book=BookService.getBookByISBN(ISBN);
                    System.out.println("here informtions of your book: ");
                    System.out.println(book.toString());


                    System.out.println("#1 if the ubdate with countity tap 1 ");
                    System.out.println("#2 if the Ubdate with out qountity tap 2");
                    int qountity =scanner.nextInt();
                    if (qountity>book.getQuantity()){
                        copiesService.AddCopies(qountity - book.getQuantity(),ISBN);
                    }
                    else {
                        System.out.println("you need to drop same copies");
                    }

                    break;
                }
                case 4:{


                }
                case 5:{
                    break;
                }
                case 6:{

                }
                case 7:{
                    break;
                }
                case 8:{

                }
                case 0 :{
                    break;
                }

                default  :
                    System.out.println("ivalid chois");
            }


        }while (choice!=0);

        scanner.close();



    }
    public static   void runApp (){

        System.out.println("----------------------Welcome Back to your Scound Hous-------------------------");
        System.out.println("# 1 show all books ");
        System.out.println("# 2 serche  book with SBIN ");
        System.out.println("# 3 Update Book ");
        System.out.println("# 3 Delete  book with ISBN ");
        System.out.println("# 5 Pureu copie of book ");
        System.out.println("# 6 broww  book ");
        System.out.println("# 7 show all typs of books ");






    }





































//        String isbn=scanner.nextLine();
//        copiesService copiesService=new copiesService();
//        BookService book=new BookService();
//          book.createBook("b4","infotestcop","crocoman","salah",3);
//        System.out.println(book1.toString());
//       int res= book.updateBook("2bb","ss","ss","ss",44);

//        System.out.println("res:"+res);
//        if (res) {
//            System.out.println("Book updated successfully.");
//        } else {
//            System.out.println("Book not found or update failed.");
//        }
//    book.createBook("33sbn","ayoub","ss","ss",4);






}