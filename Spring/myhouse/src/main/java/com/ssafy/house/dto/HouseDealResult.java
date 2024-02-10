package com.ssafy.house.dto;

public class HouseDealResult {

	private long no;
	private long aptCode;
	private String apartmentName;
	private String area;
	private int dealYear;
	private int dealMonth;
	private String dealAmount;
	private String dong;
	private String jibun;
	private String floor;
	private String email;
	private String lng;
	private String lat;
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public long getAptCode() {
		return aptCode;
	}
	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getDealYear() {
		return dealYear;
	}
	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}
	public int getDealMonth() {
		return dealMonth;
	}
	public void setDealMonth(int dealMonth) {
		this.dealMonth = dealMonth;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "HouseDealResult [no=" + no + ", aptCode=" + aptCode + ", apartmentName=" + apartmentName + ", area="
				+ area + ", dealYear=" + dealYear + ", dealMonth=" + dealMonth + ", dealAmount=" + dealAmount
				+ ", dong=" + dong + ", jibun=" + jibun + ", floor=" + floor + ", email=" + email + ", lng=" + lng
				+ ", lat=" + lat + "]";
	}
	
	
	
	
}
