package util;


public interface Constants {
	public final static String USERNAME="lusername"; 
	public final static String PWD="pwd"; 
	public final static String PHONE_NUMBER="phone_number"; 
	public final static int REQUESTCODE_REGISTER_LOGIN=0;
	public final static int REQUESTCODE_MAIN_LOGIN=1;
	public final static String IS_LOGIN="islogin";
	
	public final static String HISTORY="history";
	
	public final static String SERVER_URL="http://172.27.77.1:8080/dingcan/LuServlet?";

	public final static String SERVER_BASE_URL="http://cloud.bmob.cn/ff091cab309e41aa/";
	public final static String QUERY_USER_URL="queryUser?";
	public final static String QUERY_FOOD_URL=SERVER_BASE_URL+"queryFood?";
	public final static String QUERY_ORDER_URL=SERVER_BASE_URL+"queryOrder?";
	public final static String INSERT_ORDER_URL=SERVER_BASE_URL+"insertOrder?";
	public static final String Bmob_APPID="52ac871497b3f15d8cf509a0b4a74a17";
}
