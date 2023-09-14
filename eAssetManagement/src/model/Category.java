package model;

public class Category {
	private String assetType;
	private int lendingPeriod;
	private double lateFee;
	private int daysBanned;
	public Category(String assetType, int lendingPeriod, double lateFee, int daysBanned) {
		super();
		this.assetType = assetType;
		this.lendingPeriod = lendingPeriod;
		this.lateFee = lateFee;
		this.daysBanned = daysBanned;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public int getLendingPeriod() {
		return lendingPeriod;
	}
	public void setLendingPeriod(int lendingPeriod) {
		this.lendingPeriod = lendingPeriod;
	}
	public double getLateFee() {
		return lateFee;
	}
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	public int getDaysBanned() {
		return daysBanned;
	}
	public void setDaysBanned(int daysBanned) {
		this.daysBanned = daysBanned;
	}

}
