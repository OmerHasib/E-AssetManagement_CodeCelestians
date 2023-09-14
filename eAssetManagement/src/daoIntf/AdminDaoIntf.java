package daoIntf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import exception.InvalidCredentialsException;
import exception.RecordNotFoundException;
import model.Asset;

public interface AdminDaoIntf {
	
	void addAsset(Asset a);
	Set getCategory()throws RecordNotFoundException;
	Map overDueAssets()throws RecordNotFoundException;
	void addMsg(int Id,String s);
	List getRecords()throws RecordNotFoundException;
	void addCategory(model.Category c);
	boolean adminLogin(String adminId,String password)throws InvalidCredentialsException;
}
