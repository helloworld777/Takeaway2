package util;


public interface Constants {
    String USERNAME = "username";
    String PASSWORD = "password";
    String PHONE_NUMBER = "phone_number";
    String ADDRESS = "address";
    String REGISTER_DATE = "register_date";
    String HEADER_IMG="header_img";

    int REQUESTCODE_REGISTER_LOGIN = 0;
    int REQUESTCODE_MAIN_LOGIN = 1;
    String IS_LOGIN = "islogin";

    String HISTORY = "history";

    String SERVER_URL = "http://172.27.77.1:8080/dingcan/LuServlet?";

    String SERVER_BASE_URL = "http://cloud.bmob.cn/ff091cab309e41aa/";
    String FILE_BASE_URL="http://file.bmob.cn/";
    String QUERY_USER_URL = "queryUser?";
    String INSERT_USER_URL = "insertUser?";
    String QUERY_FOOD_URL = SERVER_BASE_URL + "queryFood?";
    String QUERY_ORDER_URL = SERVER_BASE_URL + "queryOrder?";
    String INSERT_ORDER_URL = SERVER_BASE_URL + "insertOrder?";
    String Bmob_APPID = "52ac871497b3f15d8cf509a0b4a74a17";
    String INSET_FILE_URL=SERVER_BASE_URL+"insertFile";



    int MY_NAME=0;
    int MY_PHONE=1;
    int MY_ADRESS=2;
    int MY_DATE=3;
    int EXIT=4;
}
