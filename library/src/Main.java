// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import  db.DatabaseManager;
import book.BookService;
import  book.book;
import copies.copiesService;
import copies.copies;
import client.clientService;
import client.client;

import javax.swing.*;
import java.awt.print.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import  java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import tools.validation;

public class Main {
    public static void main(String[] args) throws SQLException {
        int choice;
        Scanner scanner=new Scanner(System.in);
        do {
           choice=runApp(scanner );
            switch (choice){
                case 1:{
                  List<book> listBook =new ArrayList<book>();
                      listBook.addAll(BookService.getAllBooks());
                    System.out.println("here all books :\n"+listBook.toString());
                    break;
                }
                case 2:{
                    System.out.println("Entre the ISBN or title or author  of the book");
                    String kyword=scanner.nextLine();
                    if (BookService.getBookByISBNorTitle(kyword)!=null){
                        System.out.println(BookService.getBookByISBNorTitle(kyword).toString());
                    }

                    break;
                }
                case 3:{
                    scanner.nextLine();
                    System.out.println("enter the ISBN of the book");
                   String ISBN= scanner.nextLine();
                   book book=BookService.getBookByISBN(ISBN);
                   if (book!=null){
                       System.out.println(" here are the book's informations: ");
                       System.out.println(book.toString());
                       System.out.println("#1 if the update contains  quantity changes tap 1 ");
                       System.out.println("#2 if the update contains no quantity changes tap 2");
                         choice=validation.getValidNumber();
                         if (choice==1) {
                             System.out.println("please enter your new quantity");
                             int qountity = validation.getValidNumber();
                             if (qountity > book.getQuantity()) {
                                 copiesService.AddCopies(qountity - book.getQuantity(), ISBN);
                                 System.out.println("all copies are added ");
                             } else {
                                 System.out.println("you need to delete some copies");
                                 while ( BookService.getBookByISBN(ISBN).getQuantity() > qountity) {
                                     System.out.println("here are copies of the book");
                                     List<copies> targetlist = new ArrayList<copies>();
                                     targetlist.addAll(copiesService.searchCopiesByISBN(ISBN));
                                     for (copies Copies : targetlist) {
                                         System.out.println(Copies.toString());
                                     }
                                     System.out.println("your current quantity now is :" + BookService.getBookByISBN(ISBN).getQuantity());
                                     System.out.println("enter the id of the copies you want to delete");
                                     int id = validation.getValidNumber();
                                     int a = copiesService.deleteCopiesByID(id);
                                     if (a==0) {
                                         System.out.println(" try again the id is not found ");
                                     } else {
                                         System.out.println("done the book is deleted ");
                                     }
                                 }
                             }
                         }else {
                             scanner.nextLine();
                             System.out.println("please enter the  new title :");
                             String title =scanner.nextLine();
                             System.out.println("please enter the  new  information :");
                             String information = scanner.nextLine();
                             System.out.println("please enter the  new name of author :");
                             String author=scanner.nextLine();
                             if (BookService.updateBook(book.getISBN(),title,information,author)>0){
                                 System.out.println("your book is updated");
                             }
                         }
                   }
                   else {
                       System.out.println("not found ");
                   }
                    break;
                }
                case 4:{
                    System.out.println("Enter the ISBN of the book");
                    String isbn=scanner.nextLine();
                    if (BookService.getBookByISBN(isbn)!=null){
                        if (BookService.archivBook(isbn)==1){
                            System.out.println("Your Book is Deleted");
                        }
                        else if (BookService.archivBook(isbn)==0){
                            System.out.println("try again");
                        }
                        else if (BookService.archivBook(isbn)==2){
                            System.out.println("the book steil browing ");
                        }
                    }
                    else {
                        System.out.println("NOT FOUND");
                    }
                    break;
                }
                case 5:{
                    System.out.println("please enter ISBN :");
                    String isbn =scanner.nextLine();
                    System.out.println("please enter  title :");
                    String title =scanner.nextLine();
                    System.out.println("please enter information :");
                    String information = scanner.nextLine();
                    System.out.println("please enter author :");
                    String author=scanner.nextLine();
                    System.out.println("please enter quantity :");
                    int quantitiy=validation.getValidNumber();
                    book book=BookService.getBookByISBN(isbn);
                    while (book!=null){
                        scanner.nextLine();
                        System.out.println("The ISBN Already Found Please Enter another ISBN ");
                        isbn=scanner.nextLine();
                        book=BookService.getBookByISBN(isbn);
                    }
                    BookService.createBook(isbn,title,information,author,quantitiy);
                    break;
                }
                case 6:{
                    System.out.println("please enter ISBN :");
                    String isbn =scanner.nextLine();
                    if(copiesService.getAvailableCobyByISBN(isbn)!=null){
                        System.out.println("here is the wanted book  :");
                        System.out.println(copiesService.getAvailableCobyByISBN(isbn).toString());
                        System.out.println("please enter your name ");
                        String name=scanner.nextLine();
                        System.out.println("please enter your member Number ");
                        int memberNumber=validation.getValidNumber();
                        if( clientService.searchClientByMemberNumber(memberNumber)!=null){
                            List<Date> dates=new ArrayList<>();
                            if(!clientService.checkClientInExternal(memberNumber).isEmpty()){
                                dates.addAll(clientService.checkClientInExternal(memberNumber));
                                System.out.println("you took a book in :"+dates.get(0)+" comeback after :"+dates.get(1));
                            }
                            else {
                                System.out.println(clientService.checkClientInExternal(memberNumber));
                                System.out.println();
                                int res= BookService.browBook(copiesService.getAvailableCobyByISBN(isbn).getId(),memberNumber);
                                if (res>0){
                                    System.out.println("borrowing done");
                                }
                            }


                        }
                        else {
                            clientService.addClient(memberNumber,name);
                            System.out.println("your account created successfully");
                            int res= BookService.browBook(copiesService.getAvailableCobyByISBN(isbn).getId(),memberNumber);
                            if (res>0){
                                System.out.println("borrowing done");
                            }


                        }



                    }else {
                        System.out.println("the book is unavailable");
                    }
                    break;
                }
                case 7:{
                    scanner.nextLine();
                    System.out.println("please enter ISBN :");
                    String isbn =scanner.nextLine();
                    System.out.println("please enter your member Number ");

                    int memberNumber=validation.getValidNumber();
                   if(BookService.getCopeiByISBN(isbn,memberNumber)!=0){


                       int n=BookService.returnBook(BookService.getCopeiByISBN(isbn,memberNumber),memberNumber);


                       if (n>0){
                           System.out.println("thank you  ");
                       }else {
                           System.out.println("try again");
                       }
                   }
                   else {
                       System.out.println("no book with your information ");
                   }
                    break;
                }
                case 8:{
                if (copiesService.getStatistics().length>0){
                    copiesService.printStatistics();
                    System.out.println("the number of  Available books :"+copiesService.getStatistics()[0]);
                    System.out.println("the  number of unavailable books :"+copiesService.getStatistics()[1]);
                    System.out.println( "the number of  missing books : "+copiesService.getStatistics()[2]);
                }
                }
                case 0 :{
                    break;
                }
                default  :
                    System.out.println("invalid choice");
            }
        }while (choice!=0);
        scanner.close();
    }
    public static   int runApp (Scanner scanner){
        System.out.println("=====================================");
        System.out.println("       Welcome Back to Your");
        System.out.println("            Second Home");
        System.out.println("=====================================");
        System.out.println("1. Show All Books");
        System.out.println("2. Search Books by ISBN, Author, or Title");
        System.out.println("3. Update Book");
        System.out.println("4. Delete Book by ISBN");
        System.out.println("5. Add New Book");//valid
        System.out.println("6. Borrow a Book by ISBN");
        System.out.println("7. Return a Borrowed Book");
        System.out.println("8. Library Statistics");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------");
        System.out.print("Enter your choice: ");
        int choice=0;
        if (scanner.hasNextInt()){
            choice=scanner.nextInt();
            scanner.nextLine();
        }
        else {
            System.out.println("can take your choice between 1 and 8");
            scanner.nextLine();
            return runApp(scanner) ;
        }
       return choice;
    }



}