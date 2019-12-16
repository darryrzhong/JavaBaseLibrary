package com.example.corelibrary.app.mvp;

/**
 * 应用模块: mvp<p>
 * 类名称: 抽象的基类 Presenter<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/15  21:08
 */
public abstract class BasePresenter<M extends IModel,V extends IView> {
    protected static final String TAG = BasePresenter.class.getSimpleName();

    protected M mModel;
    protected V mView;

    /**
     * 有外部注入model层引用
     * */
    public abstract void setmModel(M mModel);

    /**
     * 由外部注入 view 层引用
     * */
    public abstract void setmView(V mView);

}
