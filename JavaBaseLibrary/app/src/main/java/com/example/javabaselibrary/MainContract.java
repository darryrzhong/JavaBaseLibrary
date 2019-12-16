package com.example.javabaselibrary;

import com.example.corelibrary.app.mvp.IModel;
import com.example.corelibrary.app.mvp.IPresenter;
import com.example.corelibrary.app.mvp.IView;

/**
 * 应用模块: <p>
 * 类名称:<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/15  23:21
 */
public interface MainContract {

    interface Presenter extends IPresenter{
        void login(String phone,String pass);
    }

    interface View extends IView<Presenter>{
        void loginSuccess(String result);
    }

    interface  Model extends IModel{
        String login(String phone,String pass);
    }
}
