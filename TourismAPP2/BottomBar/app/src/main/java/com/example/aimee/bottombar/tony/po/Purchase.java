package com.example.aimee.bottombar.tony.po;

public class Purchase {
	private String purchase_id;
	private String owner_id;
	private String package_id;
	private String purchase_date;
	private int count;
	private String status;
	private String rate_status;
	public String getPurchase_id() {
		return purchase_id;
	}
	public void setPurchase_id(String purchase_id) {
		this.purchase_id = purchase_id;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getPackage_id() {
		return package_id;
	}
	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}
	public String getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(String purchage_date) {
		this.purchase_date = purchage_date;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRate_status() {
		return rate_status;
	}
	public void setRate_status(String rate_status) {
		this.rate_status = rate_status;
	}
	
	
}
