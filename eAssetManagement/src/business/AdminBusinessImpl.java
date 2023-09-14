package business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.AdminDaoImpl;
import daoIntf.AdminDaoIntf;
import exception.InvalidCredentialsException;
import exception.OperationUnsuccessful;
import exception.RecordNotFoundException;
import model.Asset;
import model.Category;

public class AdminBusinessImpl implements AdminBusinessIntf {
	
	private AdminDaoIntf dao;
	
	

	public AdminBusinessImpl() {
		super();
		this.dao = new AdminDaoImpl();
	}

	@Override
	public void addNewAsset(Asset a) {
		dao.addAsset(a);
		
	}

	@Override
	public Set getDifferentCategory() throws OperationUnsuccessful {
		try {
			return dao.getCategory();
		} catch (RecordNotFoundException e) {
			
			throw new OperationUnsuccessful("Exception is :",e);
		}
	}

	@Override
	public Map overDueAssets() throws OperationUnsuccessful {
		try {
			return dao.overDueAssets();
		} catch (RecordNotFoundException e) {
			throw new OperationUnsuccessful("Exception is :",e);
		}
	}

	@Override
	public void addMsg(int Id, String s) {
		dao.addMsg(Id, s);
	}

	@Override
	public List getUserRecords() throws OperationUnsuccessful {
		try {
			return dao.getRecords();
		} catch (RecordNotFoundException e) {
			throw new OperationUnsuccessful("Exception is :",e);
		}
	}

	@Override
	public void addNewCategory(Category c) {
		dao.addCategory(c);	
	}

	@Override
	public boolean Login(String adminId, String password) throws OperationUnsuccessful {
		try {
			return dao.adminLogin(adminId, password);
		} catch (InvalidCredentialsException e) {
			throw new OperationUnsuccessful("Exception is :",e);
		}
	}

}
