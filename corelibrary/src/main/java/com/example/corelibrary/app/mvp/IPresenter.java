package com.example.corelibrary.app.mvp;

/**
 * 应用模块: mvp<p>
 * 类名称: IPresenter<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/15  21:07
 */
public interface IPresenter {

    /**
     * 初始化
     * */
    void start();

    /**
     * 销毁引用
     */
    void onDestroy();
}
