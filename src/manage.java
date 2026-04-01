import java.sql.*;
import java.util.Scanner;

public class manage {
    public static void main(String[] args) {
        try{
            Connection con= DriverManager.getConnection(dbconfiglib.url,dbconfiglib.username,dbconfiglib.pass);
            Scanner sc=new Scanner(System.in);
            while(true) {
                System.out.println("\n====LIBRARY MANAGEMENT SYSTEM====");
                System.out.println("1. Add Student");
                System.out.println("2. View Student");
                System.out.println("3. Delete Student");
                System.out.println("4. Add Book");
                System.out.println("5. View Books");
                System.out.println("6. Issue Book");
                System.out.println("7. Exit");
                System.out.println("Enter id of your choice: ");
                int choice=sc.nextInt();
                switch(choice){
                    case 1: //add data in db
                        System.out.println("Enter the roll no. of student");
                        int roll=sc.nextInt();
                        System.out.println("Enter the name of the student");
                        String name= sc.next();
                        System.out.println("Enter the Department");
                        String dept= sc.next();
                        String q1="INSERT INTO student1 VALUES (?,?,?)";
                        PreparedStatement ps1= con.prepareStatement(q1);
                        ps1.setInt(1,roll);
                        ps1.setString(2,name);
                        ps1.setString(3,dept);
                        int res= ps1.executeUpdate();
                        if(res>0){
                            System.out.println(res+" rows addes successfully!");
                        }
                        else{
                            System.out.println("Invalid Data");
                        }
                        break;

                    case 2: // view or search in db
                        System.out.println("Enter the roll no. of student you want to view");
                        int roll1=sc.nextInt();
                        String q2="SELECT * FROM student1 WHERE roll=?";
                        PreparedStatement ps2=con.prepareStatement(q2);
                        ps2.setInt(1,roll1);
                        ResultSet rst=ps2.executeQuery();
                        if(rst.next()){
                            System.out.println
                                    ("Roll No.: "+rst.getInt(1)+
                                            " Name: "+rst.getString(2)+"" +
                                            " Department: "+rst.getString(3));
                        }
                        else{
                            System.out.println("Invalid Data");
                        }
                        break;

                    case 3: //delete from db
                        System.out.println("Enter the rol no. of student you want to delete: ");
                        int roll2=sc.nextInt();
                        String q3="DELETE FROM student1 WHERE roll=?";
                        PreparedStatement ps3= con.prepareStatement(q3);
                        ps3.setInt(1,roll2);
                        int res3=ps3.executeUpdate();
                        if(res3>0){
                            System.out.println("Deleted Successfully!");
                        }
                        else{
                            System.out.println("Invalid Data");
                        }
                        break;

                    case 4: // add book in db
                        System.out.println("Enter the book id of the book you want to add: ");
                        int id=sc.nextInt();
                        System.out.println("Enter the name of the book: ");
                        String bname=sc.next();
                        System.out.println("Enter the quantity of the book: ");
                        int qty=sc.nextInt();
                        String q4="INSERT INTO books VALUES (?,?,?,?)";
                        int availqty=qty;
                        PreparedStatement ps4= con.prepareStatement(q4);
                        ps4.setInt(1,id);
                        ps4.setString(2,bname);
                        ps4.setInt(3,qty);
                        ps4.setInt(4,availqty);
                        int res4=ps4.executeUpdate();
                        if(res4>0){
                            System.out.println("Added Successfully!");
                        }
                        else{
                            System.out.println("Invalid Data");
                        }
                        break;

                    case 5: //view or search books
                        System.out.println("Enter the book id you want to search: ");
                        int id2= sc.nextInt();
                        String q5="SELECT * FROM books WHERE bid=?";
                        PreparedStatement ps5= con.prepareStatement(q5);
                        ps5.setInt(1,id2);
                        ResultSet rst5=ps5.executeQuery();
                        if(rst5.next()){
                            System.out.println("Book ID: "+rst5.getInt(1)+
                                    " Name: "+rst5.getString(2)+
                                    " Total Quantity: "+rst5.getInt(3)+
                                    " Available Quantity: "+rst5.getInt(4));
                            }
                        else{
                        System.out.println("Invalid Data");
                        }
                        break;

                    case 6: //issue a book
                        System.out.println("Enter the name of the book you want to issue: ");
                        String bname6=sc.next();
                        String q6="SELECT qty, availqty FROM books WHERE bname=?";
                        PreparedStatement ps6= con.prepareStatement(q6);
                        ps6.setString(1,bname6);
                        ResultSet rst6=ps6.executeQuery();
                        int total;
                        int avail ;
                        if(rst6.next()){
                            total=rst6.getInt(1);
                            avail=rst6.getInt(2);

                            if(avail>0){
                                System.out.println("Book has been issued successfully!");
                                String update="UPDATE books SET availqty=availqty-1 WHERE bname=?";
                                PreparedStatement ps7= con.prepareStatement(update);
                                ps7.setString(1,bname6);
                                ps7.executeUpdate();}
                            else{
                                System.out.println("Book is not available");
                            }
                        }
                        else{
                            System.out.println("Book not found");
                        }

                        break;

                    case 7:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid Choice");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
