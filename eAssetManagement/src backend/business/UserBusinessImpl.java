package business;

import java.util.List;

import dao.UserDaoImpl;
import daoIntf.UserDaoIntf;
import exception.AssetCannotBeBorrowedException;
import exception.NoAssetBorrowedException;
import exception.NoAssetFoundException;
import exception.OperationUnsuccessful;
import exception.UserAlreadyExistException;
import exception.UserNotFoundException;
import model.Borrow;
import model.User;



public class UserBusinessImpl implements UserBusinessIntf{
	
	private UserDaoIntf dao;
	
	

	public UserBusinessImpl() {
		super();
		this.dao = new UserDaoImpl();
	}

	@Override
	public void register(User u) throws OperationUnsuccessful
	{
		try {
			dao.register(u);
		} catch (UserAlreadyExistException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User loginByUsername(String user,String pass) throws OperationUnsuccessful {
		try {
			User u=dao.loginByUsername(user, pass);
			return u;
		} catch (UserNotFoundException e) {
			throw new OperationUnsuccessful("Exception is :",e);
			
		}
	}

	@Override
	public User loginByEmail(String user,String pass) throws OperationUnsuccessful {
		try {
			User u=dao.loginByEmail(user,pass);
			return u;
		} catch (UserNotFoundException e) {
			throw new OperationUnsuccessful("Exception is :",e);
			
		}	
	}

	@Override
	public List<Borrow> borrowedItems(int userId) throws OperationUnsuccessful {
	
		try {
			return dao.listOfBorrow(userId);
		} catch (NoAssetBorrowedException e) {
			throw new OperationUnsuccessful("Exception is :",e);
		}
	}


	
	@Override
	public void newMessages(int id) {
		
		dao.displayMessage(id);
		
	}

	@Override
	public void searchAsset(String type) throws OperationUnsuccessful {
		try {
			dao.searchAsset(type);
		} catch (NoAssetFoundException e) {
			
			throw new OperationUnsuccessful("Exception is :",e);
		}
		
	}

	@Override
	public void returnAsset(int assetId) {
		
		dao.returnAsset(assetId);
	}

	@Override
	public void borrow(int userId, int assetId) throws OperationUnsuccessful {
		try {
			dao.borrow(userId, assetId);
		} catch (AssetCannotBeBorrowedException e) {
			throw new OperationUnsuccessful("Exception is :",e);
		} catch (NoAssetBorrowedException e) {
			
			throw new OperationUnsuccessful("Exception is :",e);
		}
		
	}

	@Override
	public void userProfile(User u) {
	  
		//display details of user
	}

}
