package com.example.corelibrary.app.mvp;

import com.example.corelibrary.app.ui.BaseFragment;

/**
 * 应用模块: mvp
 * <p>
 * 类名称: BaseMvpFragment
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhong
 * @since 2019/12/15 21:00
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends BaseFragment {
    protected P mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;

    }
}
