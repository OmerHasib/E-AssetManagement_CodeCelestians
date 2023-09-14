package business;

import java.util.List;

import exception.OperationUnsuccessful;
import model.Borrow;
import model.User;

public interface UserBusinessIntf {
	
	
	
	public User loginByUsername(String user,String pass) throws OperationUnsuccessful;
	public User loginByEmail(String user,String pass)throws OperationUnsuccessful;
	public void borrow(int userId,int assetId)throws OperationUnsuccessful;
	public List<Borrow> borrowedItems(int userId)throws OperationUnsuccessful;
	public void newMessages(int userId);
	public void searchAsset(String type)throws OperationUnsuccessful;
	public void returnAsset(int assetId);
	public void register(User user) throws OperationUnsuccessful;
	void userProfile(User u);
	
	
	

}
