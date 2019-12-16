package com.example.corelibrary.app.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.corelibrary.app.ui.BaseActivity;

import butterknife.Unbinder;

/**
 * 应用模块: mvp
 * <p>
 * 类名称: BaseMvpActivity
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhong
 * @since 2019/12/15 20:59
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity
{
    protected P mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }

    }
}
