package business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import exception.InvalidCredentialsException;
import exception.OperationUnsuccessful;
import exception.RecordNotFoundException;
import model.Asset;

public interface AdminBusinessIntf {
	void addNewAsset(Asset a);
	Set getDifferentCategory()throws OperationUnsuccessful;
	Map overDueAssets()throws OperationUnsuccessful;
	void addMsg(int Id,String s);
	List getUserRecords()throws OperationUnsuccessful;
	void addNewCategory(model.Category c);
	boolean Login(String adminId,String password)throws OperationUnsuccessful;
}
