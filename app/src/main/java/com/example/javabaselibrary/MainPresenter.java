package com.example.javabaselibrary;

import com.example.corelibrary.app.mvp.BasePresenter;
import com.example.corelibrary.app.mvp.IPresenter;

/**
 * 应用模块: <p>
 * 类名称:<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/15  23:24
 */
public class MainPresenter extends BasePresenter<MainContract.Model,MainContract.View> implements MainContract.Presenter {
    @Override
    public void setmModel(MainContract.Model mModel) {
        this.mModel = mModel;
    }

    @Override
    public void setmView(MainContract.View mView) {
           this.mView = mView;
           this.mView.setPresenter(this);
    }

    @Override
    public void login(String phone, String pass) {
          mView.loginSuccess(mModel.login(phone,pass));
    }

    @Override
    public void start() {
    }

    @Override
    public void onDestroy() {
    }
}
