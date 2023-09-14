package daoIntf;

import java.util.List;

import exception.AssetCannotBeBorrowedException;
import exception.NoAssetBorrowedException;
import exception.NoAssetFoundException;
import exception.UserAlreadyExistException;
import exception.UserNotFoundException;
import model.Asset;
import model.Borrow;
import model.User;

public interface UserDaoIntf {
	
	public void register(User user) throws UserAlreadyExistException;
	public void borrow(int userId,int assetId)throws AssetCannotBeBorrowedException, NoAssetBorrowedException;
	public User loginByUsername(String userName,String password) throws UserNotFoundException;
	public User loginByEmail(String email,String password) throws UserNotFoundException;
	public List<Borrow> listOfBorrow(int userId) throws NoAssetBorrowedException;
	public String displayMessage(int userId); 
	public void searchAsset(String type) throws NoAssetFoundException;
	public void returnAsset(int assetId);



}
