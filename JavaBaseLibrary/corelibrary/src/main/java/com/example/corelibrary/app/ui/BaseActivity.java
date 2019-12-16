package com.example.corelibrary.app.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 应用模块: AppUi
 * <p>
 * 类名称: BaseActivity
 * <p>
 * 类描述: 基类Activity
 * <p>
 *
 * @author darryrzhong
 * @since 2019/12/15 20:51
 */
public abstract class BaseActivity extends AppCompatActivity
{

    protected Context mContext;

    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        mUnbinder = ButterKnife.bind(this);
        this.mContext = this;
        initView(savedInstanceState);
        initData();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        this.mContext = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    /**
     * 获取布局资源
     */
    protected abstract int getLayoutRes();
    
    /**
     * 初始化页面
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);
    
    /**
     * 加载数据
     */
    protected abstract void initData();

    
}
