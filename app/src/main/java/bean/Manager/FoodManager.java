package bean.Manager;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lu.takeaway.util.Constants;

import java.util.ArrayList;
import java.util.List;

import bean.FoodBean;

public class FoodManager {
	public final static String url = Constants.SERVER_URL;
	private static FoodManager foodManager;
	private int count = 2;
	private List<FoodBean> allfoods = new ArrayList<FoodBean>();

	public static FoodManager getDefault() {
		if (foodManager == null) {
			foodManager = new FoodManager();
		}
		return foodManager;
	}

	private FoodManager() {

	}

	public void queyrFood(int index, RequestCallBack<String> callBack) {
		String urlString = url + "flag=queryFood&index=" + index + "&count=" + count;
		new HttpUtils().send(HttpRequest.HttpMethod.GET, urlString, callBack);
	}

	public List<FoodBean> getAllfoods() {
		return allfoods;
	}

	public void setAllfoods(List<FoodBean> allfoods) {
		this.allfoods = allfoods;
	}
	public void setFoodBeansNoOrder(){
		for(FoodBean f:allfoods){
			f.setOrdered(false);
		}
	}
	public void addFoodBeans(boolean refresh, List<FoodBean> foodBeans) {
		// TODO Auto-generated method stub
		if(refresh){
			allfoods.addAll(0, foodBeans);
		}else{
			allfoods.addAll(foodBeans);
		}
	}
}
