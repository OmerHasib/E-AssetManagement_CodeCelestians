package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	
	//open the connection
	public static Connection getConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb2?useSSL=false","root","root");
			if(con!=null)
			{
				System.out.print("Connection is successful");
			}
			return con;
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		return null;
	}
	
	//close the connection
	public static void closeConnection(Connection con)
	{
		if(con!=null)
		{
			try
			{
				con.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
