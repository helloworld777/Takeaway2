package bean;

import com.lu.takeaway.bean.UserBean;

import java.util.List;

public class OrderBean {
	private int id;
	private int oid;
	private List<FoodBean> goods;
	private FoodBean foodBean;
	private int number;
	
	private String date;
	private String note;
	private String finished;
	private String dingdandate;
	public OrderBean(){
		
	}
//	public OrderBean(String date, UserBean user) {
//		
//		this.date = date;
//		this.user = user;
//	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "oid:"+oid+"date:"+date;
	}
	private UserBean user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public List<FoodBean> getGoods() {
		return goods;
	}

	public void setGoods(List<FoodBean> goods) {
		this.goods = goods;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	public FoodBean getFoodBean() {
		return foodBean;
	}
	public void setFoodBean(FoodBean foodBean) {
		this.foodBean = foodBean;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String isFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}
	public String getDingdandate() {
		return dingdandate;
	}
	public void setDingdandate(String dingdandate) {
		this.dingdandate = dingdandate;
	}
}
