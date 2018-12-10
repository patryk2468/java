import java.sql.*;
import java.util.*;
import java.lang.*;

public class DockerConnectMySQL {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/mysql";

   static final String USER = "pzamoscinski";
   static final String PASS = "password";
   
   public static void main(String[] args) throws InterruptedException{
   Connection conn = null;
   Statement stmt = null;
   Boolean baseExist = false;
   Boolean login = true;
   String sql;
   
   //Class.forName("com.mysql.cj.jdbc.Driver");
   System.out.println("Connecting to database...");
    while(login) {
        try{
          	System.out.println("Connecting to database...");
          	Class.forName("com.mysql.cj.jdbc.Driver");            
      	    conn = DriverManager.getConnection(DB_URL,USER,PASS);
            login = false; // Break out of loop because we got a connection - no exception was thrown
        }catch(SQLException se){
		Thread.sleep(10000);
            //se.printStackTrace();
        }catch(Exception e){
		Thread.sleep(10000);
            //e.printStackTrace();
        } finally {
        	System.out.println("Connecting to database...");
        }
    }
    
    try{        
    	System.out.println("Check if table in base exist");
      	DatabaseMetaData md = conn.getMetaData();
      	ResultSet rs = md.getTables(null, null, "Persons", null);
      	while (rs.next()) {
            System.out.println("Table Exist");
	    	baseExist = true;
      	}
      	rs = null;  
      	if(!baseExist){
	      	System.out.println("Creating Table");
	      	stmt = conn.createStatement();
	      	sql = "CREATE TABLE Persons (PersonID int, LastName varchar(255), FirstName varchar(255), Address varchar(255), City varchar(255) )";
	      	stmt.executeUpdate(sql);
	      	stmt = null;
	    }
	    
	Scanner read = new Scanner(System.in);
        int personId=1;
        while(true){
            System.out.println("1-show records 2-insert 3-exit");
            int choice = Integer.parseInt(System.console().readLine());
            if(choice==1){
		stmt = conn.createStatement();
      		sql = "SELECT PersonID, FirstName, LastName, Address, City FROM Persons";
      		rs = stmt.executeQuery(sql);

	       while(rs.next()){
		   	 int id  = rs.getInt("PersonID");
        		String first = rs.getString("FirstName");
         		String last = rs.getString("LastName");
	 		String address = rs.getString("Address");
	 		String city = rs.getString("City");
		
         		System.out.println("ID: " + id);
         		System.out.println(", First: " + first);
         		System.out.println(", Last: " + last);
	 		System.out.println(", Address: " + address);
	 		System.out.println(", City: " + city);
      		}
      	
      		rs.close();
      		stmt.close();
      		conn.close();
            }
            if(choice==2){
                System.out.println("type number of records");
                int liczbaRekordow = Integer.parseInt(read.next());
                for(int i=0;i<liczbaRekordow;i++){
                    System.out.println("wpisz nazwisko");
                    String lastName = read.next();
                    System.out.println("wpisz imie");
                    String firstName = read.next();
                    System.out.println("wpisz ulicę");
                    String address = read.next();
                    System.out.println("wpisz masto");
                    String city= read.next();
                    int id=personId;
                    personId++;
		    stmt = conn.createStatement();
                    sql = "INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) VALUES (id, lastName, firstName, address, city)";
                    stmt.executeUpdate(sql);	 
      		    stmt = null;
		}
            }
            if(choice==3){
                break;
            }
        }
	   
      	
   		
    }catch(SQLException se){
      	se.printStackTrace();
   	}catch(Exception e){
	    e.printStackTrace();
   	}finally{
		try{
         	if(stmt!=null) stmt.close();
      	}catch(SQLException se2){
      	
        }
      	try{
		    if(conn!=null) conn.close();
      	}catch(SQLException se){
		    se.printStackTrace();
      	}
   	}
 	}
}
