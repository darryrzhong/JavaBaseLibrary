package com.zthx.ipc;

import com.google.gson.Gson;

/**
 * 类名称: GsonUtils.java
 * <p>
 * 类描述: 序列化工具类
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/10/28 22:09
 */
public class GsonUtils
{
    private static Gson gson = new Gson();
    
    public static String toJson(Object src)
    {
        return gson.toJson(src);
    }
    
    public static <T> T fromJson(String json, Class<T> classOfT)
    {
        return gson.fromJson(json, classOfT);
    }
}
