package com.example.corelibrary.app.ui;

import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 应用模块: AppUi<p>
 * 类名称:BaseFragment<p>
 * 类描述: 继承自LazyFragment 的基类Fragment<p>
 *
 * @author darryrzhong
 * @since 2019/12/15  21:30
 */
public abstract class BaseFragment extends LazyFragment{
    protected Unbinder mUnbinder;

    @Override
    protected void initView(View rootView) {
       mUnbinder = ButterKnife.bind(this,rootView);
       initViewOrData(rootView);
    }


    @Override
    protected void onFragmentResume() {
        super.onFragmentResume();
        initNetData();
    }

    @Override
    protected void onFragmentPause() {
        super.onFragmentPause();
        onDisableNet();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY)
        {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mUnbinder = null;
    }

    /**
     * 取消网络/耗时操作
     * 根据自己业务重新此方法
     * */
    protected  void onDisableNet(){

    }

    /**
     * 初始化网络/耗时操作
     * 根据自己业务重新此方法
     * */
    protected  void initNetData(){

    }

    /**
     * 根据业务,初始化View状态 / 数据
     * */
    protected abstract void initViewOrData(View rootView);
}
