// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import  db.DatabaseManager;
import book.BookService;
import  book.book;
import copies.copiesService;
import copies.copies;
import java.awt.print.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                  List<book> listBook =new ArrayList<book>();
                      listBook.addAll(BookService.getAllBooks());
                    System.out.println(listBook.toString());
                    break;
                }
                case 2:{

                    System.out.println("Entre the isbn or title or author  of the book");
                    scanner.nextLine();
                    String kyword=scanner.nextLine();
                    System.out.println(BookService.getBookByISBNorTitle(kyword).toString());
                    break;
                }
                case 3:{
                    System.out.println("can you put the ISBN of the book");
                    scanner.nextLine();
                   String ISBN= scanner.nextLine();
                   book book=BookService.getBookByISBN(ISBN);
                   if (book!=null){
                       System.out.println("here informtions of your book: ");
                       System.out.println(book.toString());
                       System.out.println("#1 if the ubdate with countity tap 1 ");
                       System.out.println("#2 if the Ubdate with out qountity tap 2");
                         choice=scanner.nextInt();
                         if (choice==1) {
                             System.out.println("pleses entr your  new Qountity");
                             int qountity = scanner.nextInt();
                             if (qountity > book.getQuantity()) {
                                 copiesService.AddCopies(qountity - book.getQuantity(), ISBN);
                                 System.out.println("all copies is added ");

                             } else {
                                 System.out.println("you need to drop same copies");
                                 for (int i = 0; i < book.getQuantity() - qountity; i++) {
                                     System.out.println("here copies of the book");
                                     List<copies> targetlist = new ArrayList<copies>();
                                     targetlist.addAll(copiesService.searchCopiesByISBN(ISBN));
                                     for (copies Copies : targetlist) {

                                         System.out.println(Copies.toString());
                                     }
                                     System.out.println("your quontity now is :" + book.getQuantity());
                                     System.out.println("entre the id of the copies u Want delete");
                                     int id = scanner.nextInt();
                                     int a = copiesService.deleteCopiesByID(id);
                                     if (copiesService.deleteCopiesByID(id) == 0) {
                                         System.out.println("try agine the book sile ");
                                     } else {
                                         System.out.println("The book is still available! try again");
                                     }

                                 }

                             }
                         }else {
                             scanner.nextLine();
                             System.out.println("please enter your  new title :");
                             String title =scanner.nextLine();
                             System.out.println("please enter your  new  information :");
                             String information = scanner.nextLine();
                             System.out.println("please enter your  new author :");
                             String author=scanner.nextLine();

                             if (BookService.updateBook(book.getISBN(),title,information,author)>0){
                                 System.out.println("your book is ubdated");
                             }



                         }
                   }
                   else {
                       System.out.println("not found any book with your ISBN");
                   }



                    break;
                }
                case 4:{
                    System.out.println("Entre the ISBN of the book");
                    scanner.nextLine();
                    String isbn=scanner.nextLine();

                    if (BookService.getBookByISBN(isbn)!=null){

                        if (BookService.deleteBook(isbn)){
                            System.out.println("Your Book is Deleted");
                        }
                        else {
                            System.out.println("try agin");
                        }

                    }
                    else {
                        System.out.println("NOT FOUND");
                    }




                }
                case 5:{
                        scanner.nextLine();
                    System.out.println("please enter ISBN :");
                    String isbn =scanner.nextLine();
                    System.out.println("please enter  title :");
                    String title =scanner.nextLine();
                    System.out.println("please enter information :");
                    String information = scanner.nextLine();
                    System.out.println("please enter author :");
                    String author=scanner.nextLine();
                    System.out.println("please enter quantitiy :");
                    int quantitiy=scanner.nextInt();
                    System.out.println("quantity :"+quantitiy);
                    book book=BookService.getBookByISBN(isbn);
                    while (book!=null){
                        scanner.nextLine();
                        System.out.println("The ISBN Already Found Please Enter anther ISBN ");
                        isbn=scanner.nextLine();
                        book=BookService.getBookByISBN(isbn);
                    }
                    BookService.createBook(isbn,title,information,author,quantitiy);





                    break;
                }
                case 6:{
                    scanner.nextLine();
                    System.out.println("please enter ISBN :");
                    String isbn =scanner.nextLine();
                    if(copiesService.getAvailableCobyByISBN(isbn)!=null){
                        System.out.println("here your book you want it :");
                        System.out.println(copiesService.getAvailableCobyByISBN(isbn).toString());
                       int res= BookService.browBook(copiesService.getAvailableCobyByISBN(isbn).getId(),2,"2023-09-06","2023-10-06");
                       if (res>0){
                           System.out.println("brrowed done");
                       }
                    }

                }
                case 7:{
                    break;
                }
                case 8:{
                    int a=2;
                    int b=3 ;
                    System.out.println(a+b);

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
        System.out.println("# 2 recherche book with SBIN or author or title");
        System.out.println("# 3 Update Book ");
        System.out.println("# 4 Delete  book with ISBN ");
        System.out.println("# 4 Delete copy of book with ISBN ");
        System.out.println("# 5 add new  book ");
        System.out.println("# 6 broww  book ");
        System.out.println("# 0 exit ");
        System.out.println("________________________________________________________________________________");






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