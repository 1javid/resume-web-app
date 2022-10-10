package dao.inter;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionAbstract {

     protected static Connection connect() throws Exception {

         Class.forName("com.mysql.cj.Driver");
         Class.forName("com.mysql.cj.jdbc.Driver");
         String url = "jdbc:mysql://localhost:3306/resume";
         String username = "root";
         String password = "Vusacanu123!";

         return DriverManager.getConnection(url, username, password);
    }
}
