package com.example.javabaselibrary;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.corelibrary.app.mvp.BaseMvpActivity;

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/12/16 13:46
 */
public class LoginActivity extends BaseMvpActivity<MainContract.Presenter>  {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
      LoginFragment loginFragment = LoginFragment.newInstance();
      UIUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrameLayout);
   MainPresenter   mPresenter = new MainPresenter();
   mPresenter.setmModel(new MainModel());
   mPresenter.setmView(loginFragment);
    }

}
