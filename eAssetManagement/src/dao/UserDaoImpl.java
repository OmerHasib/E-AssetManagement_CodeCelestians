package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import daoIntf.UserDaoIntf;
import dbutil.DBConnection;
import exception.AssetCannotBeBorrowedException;
import exception.NoAssetBorrowedException;
import exception.UserAlreadyExistException;
import model.User;
import model.Asset;
import model.Borrow;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;


public class UserDaoImpl implements UserDaoIntf{

	Set<User> set;
	List<Asset>lst;
	List<Borrow>br;
	
	
	public UserDaoImpl()
	{
		this.set=new HashSet<User>();
		this.lst=new ArrayList<Asset>();
		this.br=new ArrayList<Borrow>();
		
	}
	
	
	//To register the User
	@Override
	public void register(User user) throws UserAlreadyExistException {
		
		
		Connection con = null;
		try {
			con = DBConnection.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(con!=null)
		{
			String str="insert into User values(?,?,?,?,?,?,?)";
			if(!userAlreadyexistByEmail(user.getuEmail())
					|| !userAlreadyexistByUsername(user.getuEmail()))
			{
			String encode=encodedPass(user.getuPassword());
			try(PreparedStatement p=con.prepareStatement(str))
			{
				p.setInt(1, user.getUid());
				p.setString(2, user.getuName());
				p.setString(3, user.getuRole());
				p.setLong(4, user.getuTelephone());
				p.setString(5,user.getuEmail());
				p.setString(6, user.getUsername());
				p.setString(7, encode);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally {
				DBConnection.closeConnection(con);
			}
		}
			else
			{
				//consequence
				throw new UserAlreadyExistException("User Already Exist");
				
			}
		}
		
		
		
	}
	

	//checking if user already exist with the same email
	
	
	private boolean userAlreadyexistByEmail(String getuEmail) {
		Connection con=DBConnection.getConnection();
		if(con!=null)
		{
			String str="Select Email from User where email = ?";
			
			try(PreparedStatement preparedStatement = con.prepareStatement(str);
					)
			{
				// Set the user input as a parameter in the prepared statement
	            preparedStatement.setString(1, getuEmail);

	            // Execute the query
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	            	return true;
	            }
			}catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		return false;
	}
	
	//checking if user already exist with the same user name
	
	private boolean userAlreadyexistByUsername(String username)
	{
		Connection con=DBConnection.getConnection();
		if(con!=null)
		{
			String str1="Select username from User where username = ?";
			try(PreparedStatement preparedStatement = con.prepareStatement(str1);)
			{
				// Set the user input as a parameter in the prepared statement
	            preparedStatement.setString(1, username);

	            // Execute the query
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	            	return true;
	            }
			}catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		
		return false;
	}
	
	
	//for security purpose encoding password
	
	private String encodedPass(String getuPassword) {
		String salt=generateSalt();
		try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt.getBytes());
            byte[] hashedPasswordBytes = messageDigest.digest(getuPassword.getBytes());

            // Convert the hashed password to a base64-encoded string
            return Base64.getEncoder().encodeToString(hashedPasswordBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
		
	}
	
	// Generate a salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    //<!--password encoded done-->
    
    
	@Override
	public void borrow(int userId,int assetId) throws AssetCannotBeBorrowedException, NoAssetBorrowedException 
{
		
		/*in order to borrow new asset to the user it is necessary to check if user is not a defaulter i.e. he should not
		 have any asset borrowed which is overdue */
		List<Borrow> as=listOfBorrow(userId);
		if(isDefaulter(as))
		{
			//Can't issue anymore asset
throw new AssetCannotBeBorrowedException("Asset cannot be borrowed due to previous fines");
		}
		else
		{
		Connection con=DBConnection.getConnection();
		if(con!=null)
		{
		String str2="Select Type from Asset where assetId=?";
		String type=""; int days=0;
		try(PreparedStatement preparedStatement = con.prepareStatement(str2);)
		{
			// Set the user input as a parameter in the prepared statement
            preparedStatement.setInt(1, assetId);

            // Execute the query
            ResultSet resultSet1 = preparedStatement.executeQuery();
            type = resultSet1.getString("Type");
		}catch(SQLException e)
		{
		e.printStackTrace();
		}
		//Step2: Now find What is the lending period of particular asset Type
		String str3="Select lendingPeriod from Category where assetType=?";
		try(PreparedStatement preparedStatement1 = con.prepareStatement(str3);)
		{
			// Set the user input as a parameter in the prepared statement
            preparedStatement1.setString(1, type);

            // Execute the query
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            days = resultSet1.getInt("lendingPeriod");
		}catch(SQLException e)
		{
		e.printStackTrace();
		}
		String str1= "INSERT INTO Borrow (userId, assetId, dateissued,returningDate) VALUES (?, ?, ?,?)";
				
				// Get the current date and time
		        Date currentDate = new Date();
		        // Calculate returning date according to Category of asset
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(currentDate);
		        calendar.add(Calendar.DATE, days);
		        Date  returnDate= calendar.getTime();
				// Create a PreparedStatement to safely insert data
				try(PreparedStatement preparedStatement = con.prepareStatement(str1);)
				{
	            preparedStatement.setInt(1, userId);
	            preparedStatement.setInt(2, assetId);
	            preparedStatement.setDate(3, new java.sql.Date(currentDate.getTime())); // Convert to java.sql.Date
	            preparedStatement.setDate(4, new java.sql.Date(returnDate.getTime()));
				}catch(SQLException e)
				{
					e.printStackTrace();
					}	
		}	
		
		}	
	}
	
	
	private boolean isDefaulter(List<Borrow> as) {
		
	     //to get the current Date
		 Date today = new Date();
	        // Step 4: Iterate through the list of dates and compare each date with today's date
	        for (Borrow b : as ) {
	            if (b.getReturningDate().before(today)) {
	            	return true;
	            }
	               
	            }
		return false;
	}

	//<!--Login---->

	@Override
	public User loginByUsername(String userName, String password) {
		Connection con=DBConnection.getConnection();
		password=encodedPass(password);
		if(con!=null)
		{
			String str1="Select password from User where username = ?";
			try(PreparedStatement preparedStatement = con.prepareStatement(str1);)
			{
				// Set the user input as a parameter in the prepared statement
	            preparedStatement.setString(1, userName);

	            // Execute the query
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (!resultSet.next()) {
	            	// user doesn't exist
	            }
	            else
	            {
	                  String pass = resultSet.getString("password");
	                  if(password==pass)
	                  {
	                	  //response to send to login page;
	                  }
	                  else
	                  {
	                	  //wrong credential
	                  }
	                  
	            }
	            
	            
			
		
		} catch(SQLException e)
			{
			e.printStackTrace();
			}
		}
		return null;
		

	}
	@Override
	public User loginByEmail(String email, String password) {
		Connection con=DBConnection.getConnection();
		password=encodedPass(password);
		if(con!=null)
		{
			String str1="Select password from User where email = ?";
			try(PreparedStatement preparedStatement = con.prepareStatement(str1);)
			{
				// Set the user input as a parameter in the prepared statement
	            preparedStatement.setString(1, email);

	            // Execute the query
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (!resultSet.next()) {
	            	// user doesn't exist
	            }
	            else
	            {
	                  String pass = resultSet.getString("password");
	                  if(password==pass)
	                  {
	                	  //response to send to login page;
	                  }
	                  else
	                  {
	                	  //wrong credential
	                  }
	                  
	            }
	            
	            
			
		
		}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return null;
		

	}

	//<!--Login done--->
	
	
	
	//List Of Borrow
	@Override
	public List<Borrow> listOfBorrow(int userId) {
		
		Connection con=DBConnection.getConnection();
		if(con!=null)
		{
			
			String str1="Select password from Borrow where userID=?";
			try(PreparedStatement preparedStatement = con.prepareStatement(str1);)
			{
				// Set the user input as a parameter in the prepared statement
	            preparedStatement.setInt(1, userId);

	            // Execute the query
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (!resultSet.next()) {
	            	//nothing is borrowed so far
	            }
	            else
	            {
	            	while(resultSet.next())
	            	{
	            		br.add(new Borrow(resultSet.getInt("userId"),resultSet.getInt("assetId"),
	            				resultSet.getDate("dateissued"),resultSet.getDate("returningDate")));
	            		
	            		
	            	}
	            	
	            	
	            		
	             }
	            	
	            }
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			}
		return br;	
	}

	public void detailsOfUser() {
		//display details of user
		
		
		
	}

	public String displayMessage(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String message = null;

        try {
            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to retrieve the message based on userId
            String sql = "SELECT message FROM User WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a message was found and retrieve it
            if (resultSet.next()) {
                message = resultSet.getString("message");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the result set, prepared statement, and the connection
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    DBConnection.closeConnection(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return message;
    }

	@Override
	public void searchAsset(String type) {
		Connection con=DBConnection.getConnection();
		if(con!=null)
		{
			String str1="Select * from Asset where Type = ? AND isAvailable = 'yes' ";
			try(PreparedStatement preparedStatement = con.prepareStatement(str1);)
			{
				// Set the user input as a parameter in the prepared statement
	            preparedStatement.setString(1, type);

	            // Execute the query
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (!resultSet.next()) {
	            	// No Asset is available currently
	            	}
	            else
	            {
	            	while(resultSet.next())
	            	{
	            		Asset a=new Asset(resultSet.getInt("assetId"),
	            				resultSet.getString("assetName"),
	            				resultSet.getString("Type"),
	            				resultSet.getString("Description"),
	            				resultSet.getDate("dateadded"),
	            				resultSet.getBoolean("isAvailable"));
	            		
	            		lst.add(a);
	            		
	            	}
	            	//display searched assets
	            	for(Asset a:lst)
	            		System.out.println(a);
	            	
	            }
			}catch(SQLException e)
			{
			e.printStackTrace();
			}
		}
		
	}

	@Override
	public void returnAsset(int assetId) {
		Connection con=DBConnection.getConnection();
		if(con!=null)
		{
			String str1="UPDATE Asset SET isAvailable ='yes' WHERE assetId = ?";
			try(PreparedStatement preparedStatement = con.prepareStatement(str1);)
			{
				preparedStatement.setInt(1, assetId);
			}catch(SQLException e)
			{
			e.printStackTrace();
			}
			
			String str2="Delete from Borrow WHERE assetId = ?";
			try(PreparedStatement preparedStatement = con.prepareStatement(str2);)
			{
				preparedStatement.setInt(1, assetId);
			}catch(SQLException e)
			{
			e.printStackTrace();
			}
		}
			
			
	}
}
	
