package com.example.corelibrary.app.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.corelibrary.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 应用模块 : AppUi<p>
 * 类名称: BaseDialogFragment.java<p>
 * 类描述: 基础通用的dialog<p>
 *
 * @author darryrzhong
 * @since 2019/12/16 14:08
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected View rootView = null;
    protected Unbinder unbinder;
    public Activity mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ActionSheetDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        unbinder = ButterKnife.bind(this,rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
        unbinder = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mContext = null;
    }

    protected abstract void initData();

    protected abstract void initView(View rootView);

    protected abstract int getLayoutRes();

}
