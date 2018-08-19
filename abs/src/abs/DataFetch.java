package abs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataFetch {
	ResultSet rs2;
    Connection con;
    Statement stmt;
   void fetchData(){
      try{
          Class.forName("com.mysql.jdbc.Driver");
         con=DriverManager.getConnection(  
          "jdbc:mysql://127.0.0.1:3306/users","root","kartikay");    
          stmt=con.createStatement();  
          rs2=stmt.executeQuery("select * from user");
      }
      catch(Exception ex){
          System.out.println(ex);
      }                           
   }
}
