package model;

import java.util.Date;

public class Borrow {
	private int userId;
	private int assetId;
	private Date dateissued;
	private Date returningDate;
	public Borrow(int userId, int assetId, Date dateissued, Date returningDate) {
		super();
		this.userId = userId;
		this.assetId = assetId;
		this.dateissued = dateissued;
		this.returningDate = returningDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public Date getDateissued() {
		return dateissued;
	}
	public void setDateissued(Date dateissued) {
		this.dateissued = dateissued;
	}
	public Date getReturningDate() {
		return returningDate;
	}
	public void setReturningDate(Date returningDate) {
		this.returningDate = returningDate;
	}
	

}
