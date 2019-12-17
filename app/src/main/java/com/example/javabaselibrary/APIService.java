package com.example.javabaselibrary;

import com.example.corelibrary.net.BaseResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 应用模块 : <p>
 * 类名称: .java<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/17 14:11
 */
public interface APIService {

    String BASE_URL = "http://core.xiaotibang.com";
    /**
     * 获取验证码
     *
     * @param route 请求体
     */
    @POST("/app/auth/captcha")
    Observable<Phone> postPhone(@Body RequestBody route);
}
