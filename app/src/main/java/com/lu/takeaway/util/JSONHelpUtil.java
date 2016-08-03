package com.lu.takeaway.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author laichunling
 * @Package com.goopai.base.http
 * @Description: ${TODO}(json 解析辅助类工具，用于处理服务器解析不规范问题)
 * @date 2015/12/4 09:45
 */
public class JSONHelpUtil {
    /**
     * @Fields DEFAULT_STRING_VALUE : TODO 默认 String value
     */
    private static final String DEFAULT_STRING_VALUE = "";

    /**
     * @Fields DEFAULT_INT_VALUE : TODO 默认 int value
     */
    private static final int DEFAULT_INT_VALUE = 0;

    private JSONObject jo = null;
    public JSONHelpUtil(){

    }
    public JSONHelpUtil(JSONObject json) {
        // TODO Auto-generated constructor stub
        this.setJo(json);
    }

    public JSONObject getJSONObject(String keyName) {
        if (getJo() == null || keyName == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        if (getJo().has(keyName)) {
            try {
                json = getJo().getJSONObject(keyName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public JSONArray getJSONArray(String keyName) {
        if (getJo() == null || keyName == null) {
            return new JSONArray();
        }
        JSONArray jsonArray = new JSONArray();
        if (getJo().has(keyName)) {
            try {
                jsonArray = getJo().getJSONArray(keyName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    public Object getObject(String keyName) {
        Object object = new Object();
        try {
            object = getJo().get(keyName);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    public String getString(String keyName) {
        if (getJo() == null || keyName == null) {
            return DEFAULT_STRING_VALUE;
        }
        String value = DEFAULT_STRING_VALUE;
        if (getJo().has(keyName)) {
            try {
                value = getJo().getString(keyName);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (value == null || "null".equals(value)) {
                value = DEFAULT_STRING_VALUE;
            }
        }
        return value;
    }

    public int getInt(String keyName) {
        if (getJo() == null || keyName == null) {
            return DEFAULT_INT_VALUE;
        }
        int value = DEFAULT_INT_VALUE;
        if (getJo().has(keyName)) {
            try {
                value = getJo().getInt(keyName);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return value;
    }

    public boolean getBoolean(String keyName) {
        if (getJo() == null) {
            return false;
        }
        boolean bool = false;
        return bool;
    }

    public JSONObject getJo() {
        return jo;
    }

    public void setJo(JSONObject jo) {
        this.jo = jo;
    }
}
