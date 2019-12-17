package com.example.corelibrary.db.mmkv;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencent.mmkv.MMKV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用模块 : 数据存储<p>
 * 类名称: MmkvHelper.java<p>
 * 类描述: 扩展腾讯的MMKV<p>
 *
 * @author darryrzhong
 * @since 2019/12/17 16:32
 */
public class MmkvHelper {

    private  MMKV mmkv;

    private MmkvHelper(){

    }

    public void initialize(Context context){
        MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
    }


    /**
     * 存入map集合
     *
     * @param key 标识
     * @param map 数据集合
     */
    public   void saveInfo(String key, Map<String, Object> map)
    {
        Gson gson = new Gson();
        JSONArray mJsonArray = new JSONArray();
        JSONObject object = null;
        try
        {
            object = new JSONObject(gson.toJson(map));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        mJsonArray.put(object);
       mmkv.encode(key, mJsonArray.toString());
    }

    /**
     * 获取map集合
     *
     * @param key 标识
     */
    public  Map<String, String> getInfo(String key)
    {
        Map<String, String> itemMap = new HashMap<>(16);
        String result = mmkv.decodeString(key, "");
        try
        {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject itemObject = array.getJSONObject(i);

                JSONArray names = itemObject.names();
                if (names != null)
                {
                    for (int j = 0; j < names.length(); j++)
                    {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
            }
        }
        catch (JSONException e)
        {

        }
        return itemMap;
    }

    /**
     * 扩展MMKV类使其支持对象存储
     *
     */

    public   <T> T getObject(String key, Class<T> t)
    {
        String str = mmkv.decodeString(key, null);
        if (!TextUtils.isEmpty(str))
        {
            return new GsonBuilder().create().fromJson(str, t);
        }
        else
        {
            return null;
        }
    }

    public MMKV getMmkv() {
        return mmkv;
    }

    public static MmkvHelper getInstance(){
        return MmkvHolder.INSTANCE;
    }
    private static class MmkvHolder{
        private static final MmkvHelper INSTANCE = new MmkvHelper();
    }


}
