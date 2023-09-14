package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import daoIntf.AdminDaoIntf;
import dbutil.DBConnection;
import exception.InvalidCredentialsException;
import exception.RecordNotFoundException;
import model.Asset;
import model.Borrow;
import model.Category;

public class AdminDaoImpl implements AdminDaoIntf{

	@Override
	public void addAsset(Asset a) {
		// Add an asset to the database
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to insert the asset into the database
            String sql = "INSERT INTO assets VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set the values for the prepared statement
            preparedStatement.setLong(1, a.getAssetId());
            preparedStatement.setString(2, a.getAssetName());
            preparedStatement.setString(3, a.getType());
            preparedStatement.setString(4, a.getDescription());
            preparedStatement.setDate(5, (Date) a.getDateadded());
            preparedStatement.setBoolean(6, a.getIsAvailable());

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the prepared statement and the connection
            try {
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
		
	}

	@Override
	public Set<String> getCategory() throws RecordNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Set<String> categories = new HashSet<>();

        try {
            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to retrieve all categories
            String sql = "SELECT category_type FROM category";
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Process the result set and add categories to the Set
            while (resultSet.next()) {
                String category = resultSet.getString("category_type");
                categories.add(category);
            }

            // Check if no categories were found and throw an exception if needed
            if (categories.isEmpty()) {
                throw new RecordNotFoundException("No categories found.");
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

        return categories;
    }

	@Override
	public void addCategory(Category c) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to insert a category into the category table
            String sql = "INSERT INTO category (category_type, lendingPeriod, lateFee, daysBanned) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set the values for the prepared statement
            preparedStatement.setString(1,c.getAssetType());
            preparedStatement.setInt(2, c.getLendingPeriod());
            preparedStatement.setDouble(3, c.getLateFee());
            preparedStatement.setInt(4, c.getDaysBanned());

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the prepared statement and the connection
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    DBConnection.closeConnection(connection);
                }
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
	}

	@Override
	public Map<Integer, Map<Integer, Date>> overDueAssets() throws RecordNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<Integer, Map<Integer, Date>> usersOverDueAssetsMap = new HashMap<>();

        try {
            // Get the current date
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to retrieve over-due assets for all users
            String sql = "SELECT b.userId, b.assetId, a.name, a.description, a.value, b.returningDate " +
                         "FROM borrow b " +
                         "JOIN assets a ON b.assetId = a.assetId " +
                         "WHERE b.returningDate < ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, currentDate);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Process the result set and populate the usersOverDueAssetsMap
            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                int assetId = resultSet.getInt("assetId");
                String assetName = resultSet.getString("name");
                String assetDescription = resultSet.getString("description");
                double assetValue = resultSet.getDouble("value");
                Date returningDate = resultSet.getDate("returningDate");

                

                // Check if the user already exists in the map
                if (!usersOverDueAssetsMap.containsKey(userId)) {
                    usersOverDueAssetsMap.put(userId, new HashMap<>());
                }

                // Add the asset and its returning date to the user's map
                usersOverDueAssetsMap.get(userId).put(assetId, returningDate);
            }

            // Check if no over-due assets were found and throw an exception if needed
            if (usersOverDueAssetsMap.isEmpty()) {
                throw new RecordNotFoundException("No over-due assets found for any user.");
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

        return usersOverDueAssetsMap;
    }


	@Override
	public void addMsg(int id,String message) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to insert a message into the User table
            String sql = "UPDATE User SET message = CONCAT(IFNULL(message, ''), ?) WHERE user_id = ?"; 
            preparedStatement = connection.prepareStatement(sql);

            // Set the values for the prepared statement
            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, id);

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the prepared statement and the connection
            try {
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
    }

	@Override
	public List<Borrow> getRecords() throws RecordNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Borrow> records = new ArrayList<>();

        try {
            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to retrieve all records from the borrow table
            String sql = "SELECT userId, assetId, dateissued, returningDate FROM borrow";
            preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Process the result set and populate the records list
            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                int assetId = resultSet.getInt("assetId");
                Date dateIssued = resultSet.getDate("dateissued");
                Date returningDate = resultSet.getDate("returningDate");

                Borrow record = new Borrow(userId, assetId, dateIssued, returningDate);
                records.add(record);
            }

            // Check if no records were found and throw an exception if needed
            if (records.isEmpty()) {
                throw new RecordNotFoundException("No records found in the borrow table.");
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

        return records;
    }

	@Override
	public boolean adminLogin(String adminId, String password) throws InvalidCredentialsException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isValid = false;

        try {
            // Get a database connection
            connection = DBConnection.getConnection();

            // SQL query to check admin credentials
            String sql = "SELECT * FROM admin WHERE adminid = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, adminId);
            preparedStatement.setString(2, password);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if admin credentials are valid
            isValid = resultSet.next();

            // If not valid, throw an exception
            if (!isValid) {
                throw new InvalidCredentialsException("Invalid admin credentials.");
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

        return isValid;
    }

}
