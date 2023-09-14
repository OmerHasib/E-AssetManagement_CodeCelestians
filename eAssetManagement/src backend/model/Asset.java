package model;

import java.util.Date;

public class Asset {
	private int assetId;
	private String assetName;
	private String Type;
	private String Description;
	private Date dateadded;
	private Boolean isAvailable;
	public Asset() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Asset(int assetId, String assetName, String type, String description, Date dateadded, Boolean isAvailable) {
		super();
		this.assetId = assetId;
		this.assetName = assetName;
		Type = type;
		Description = description;
		this.dateadded = dateadded;
		this.isAvailable = isAvailable;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Date getDateadded() {
		return dateadded;
	}
	public void setDateadded(Date dateadded) {
		this.dateadded = dateadded;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	

}
