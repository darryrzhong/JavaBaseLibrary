package com.example.corelibrary.app.mvp;

/**
 * 应用模块: mvp
 * <p>
 * 类名称: IView
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhong
 * @since 2019/12/15 21:10
 */
public interface IView<P>
{
    /**
     * 将P 层 与V层关联
     */
    void setPresenter(P presenter);
    
}
