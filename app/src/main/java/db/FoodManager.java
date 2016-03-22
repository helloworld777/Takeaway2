package db;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bean.FoodBean;
import util.Constants;
import util.LogUtil;

public class FoodManager {
	private static final String TAG = "FoodManager";
	private List<FoodBean> allfoods = new ArrayList<FoodBean>();
	private List<FoodBean> currentFoodBeans= new ArrayList<FoodBean>();
	
	private List<FoodBean> searchFoodBeans=new ArrayList<FoodBean>();
	
	public List<FoodBean> getSearchFoodBeans() {
		return searchFoodBeans;
	}

	public void setSearchFoodBeans(List<FoodBean> searchFoodBeans) {
		this.searchFoodBeans = searchFoodBeans;
	}

	public void searchFoodBean(String key){
		searchFoodBeans.clear();
		for(FoodBean bean:allfoods){
			if(bean.getName().contains(key)){
				searchFoodBeans.add(bean);
			}
			
		}
		
	}
	
	public List<FoodBean> getCurrentFoodBeans() {
		return currentFoodBeans;
	}
	public void setCurrentFoodBeans(List<FoodBean> currentFoodBeans) {
		this.currentFoodBeans = currentFoodBeans;
	}
	private static FoodManager foodManager;
//	private int[] img = new int[] { R.drawable.hongshaoyu, R.drawable.koushuiji,
//			R.drawable.larouxianggan, R.drawable.laziji, R.drawable.qiancengbing,
//			R.drawable.qianjiaoxianggu,
//			R.drawable.shuizhuniurou, R.drawable.sulafen, R.drawable.xihongshidanmian,
//			R.drawable.yiwanxiang };
	private FoodManager(){
		
	}
	public static FoodManager getDefault(){
		if(foodManager==null){
			foodManager=new FoodManager();
		}
		return foodManager;
	}
	public void getAllFoodBean(Context context) {
		allfoods.clear();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("foodname/cai.txt")));
			StringBuffer sBuffer = new StringBuffer();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sBuffer.append(line);

			}
			LogUtil.d(TAG, sBuffer.toString());
			JSONObject object = new JSONObject(sBuffer.toString());
			JSONArray jsonArray = object.getJSONArray("foods");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				String name = jsonObject.getString("name");
				double price = jsonObject.getDouble("price");
				double salePrice = jsonObject.getDouble("salePrice");
//				FoodBean foodBean = new FoodBean(name, price, salePrice, img[i]);
//				allfoods.add(foodBean);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.d(TAG, "Exception");
		}

	}
	public void refreshData(boolean isRefresh){
		int index=currentFoodBeans.size();
		LogUtil.d(TAG, "index:"+index);
		LogUtil.d(TAG, "allfoods.size():"+allfoods.size());
		
		for(int i=0;i<2;i++){
			if(index<allfoods.size()){
				
				if(isRefresh){
					currentFoodBeans.add(0,allfoods.get(index++));
				}else{
					currentFoodBeans.add(allfoods.get(index++));
				}
				
			}
			
		}
	}
	public void refreshDataFromNet(boolean isRefresh){
		int index=currentFoodBeans.size();
		LogUtil.d(TAG, "index:"+index);
		LogUtil.d(TAG, "allfoods.size():"+allfoods.size());
		
		
	}
	public final static String url= Constants.SERVER_URL;
	private int count=2;
	public void queyrFood(int index,RequestCallBack<String> callBack){
		String urlString=url+"flag=queryFood&index="+index+"&count="+count;
		new HttpUtils().send(HttpRequest.HttpMethod.GET, urlString, callBack);
	}
}
