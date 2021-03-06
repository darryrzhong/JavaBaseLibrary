package com.example.javabaselibrary;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.corelibrary.app.mvp.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button)
    Button button;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        MainPresenter presenter = new MainPresenter();
        presenter.setmModel(new MainModel());
        presenter.setmView(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void loginSuccess(String result) {
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (mPresenter == null) {
            mPresenter = presenter;
        }
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        String name = editText.getText().toString();
        String pass = editText2.getText().toString();
        mPresenter.login(name,pass);
    }
}
