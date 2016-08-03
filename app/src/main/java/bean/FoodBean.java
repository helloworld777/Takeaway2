package bean;


import com.lu.takeaway.util.FormatUti;

public class FoodBean 	{
	private String name;
	private double price;
	private int res;
	private double sale;
	private boolean isOrdered;
	private int number;
	private String dingdandate;


	private String picture;
	private String note;
	
	private int id;
	public String getName() {
		return name;
	}
	public FoodBean(){
		
	}
	public FoodBean(String name, double price, double sale, String picture,String note) {
		super();
		this.name = name;
		this.price = price;
		this.sale = sale;
		this.setPicture(picture);
		this.note=note;
	}
//	public FoodBean(String name, double price, double salePrice, int res) {
//		super();
//		this.name = name;
//		this.price = price;
//		this.salePrice = salePrice;
//		this.setRes(res);
//	}

	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
//	public void setSalePrice(double sale) {
//		this.salePrice = sale;
//	}
	public int getRes() {
		return res;
	}
	public void setRes(int res) {
		this.res = res;
	}
	public void setSale(double sale) {
		this.sale=sale;

	}
	public double getSalePrice(){
		return Double.parseDouble(FormatUti.formatNumber(price*sale));
	}
	public String getSalePriceText(){
		return FormatUti.formatNumber(price*sale);
	}
	public boolean isOrdered() {
		return isOrdered;
	}
	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDingdandate() {
		return dingdandate;
	}
	public void setDingdandate(String dingdandate) {
		this.dingdandate = dingdandate;
	}
	public double getSale() {
		return sale;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
//	private double salePrice;
//	private Bitmap picture;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
