package bean.Manager;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import com.lu.takeaway.bean.UserBean;
import util.Constants;
import util.SaveDataUtil;

public class UserBeanManager {
	public final static String USERNAME = "lusername";
	private final static String PASSWORD = "passowrd";
	private String url = Constants.SERVER_URL;

	public boolean isLogin(Context context) {

		return !SaveDataUtil.getString(context, USERNAME).equals("") && !SaveDataUtil.getString(context, PASSWORD).equals("");
	}

	public void saveLoginedUser(Context context, String value, String password) {
		SaveDataUtil.setString(context, USERNAME, value);
		SaveDataUtil.setString(context, PASSWORD, password);
	}

	public void login(UserBean userBean, RequestCallBack<String> callBack) {
		String myurl = url + "flag=login&username=" + userBean.getUsername() + "&password=" + userBean.getPassword();
		// HttpUtils httpUtils=new HttpUtils();
		new HttpUtils().send(HttpRequest.HttpMethod.GET, myurl, callBack);
	}
}
