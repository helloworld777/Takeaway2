package util;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/3/30.
 */
public class DataVaildUtil {

    public static boolean isDataVailed(Context context, String data, String tip) {

        return isDataVailed(context,new String[]{data},new String[]{tip});
    }
    public static boolean isDataVailed(Context context, String[] datas, String[] tips) {
        for (int i = 0; i < datas.length; i++) {
            if (TextUtils.isEmpty(datas[i])) {
                if (i < tips.length && context != null) {
                    TopNoticeDialog.showToast(context, tips[i],TopNoticeDialog.TipType.FAILURE_TIP);
                }
                return false;
            }
        }
        return true;
    }

    public static boolean isDataVailed(String[] datas, String[] tips) {
        return isDataVailed(null, datas, tips);
    }
    /**
     * 验证手机号码
     *
     * @return
     */
    public static boolean checkMobileNumber(Context context,String mobileNumber) {
        boolean flag = false;
        if(TextUtils.isEmpty(mobileNumber)){
            TopNoticeDialog.showToast(context,"号码格式不正确", TopNoticeDialog.TipType.FAILURE_TIP);
            return flag;
        }
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
            Debug.d("checkMobileNumber","....................flag");
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
            TopNoticeDialog.showToast(context,"号码格式不正确", TopNoticeDialog.TipType.FAILURE_TIP);
            flag = true;
        }
        return flag;
    }
}
