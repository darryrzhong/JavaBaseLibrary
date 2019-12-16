package com.example.javabaselibrary;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.corelibrary.app.mvp.BaseMvpFragment;
import com.example.corelibrary.utils.DeviceUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名称: .java<p>
 * 类描述: <p>
 * Company: 江苏智体互享科技有限公司<p>
 *
 * @author darryrzhong
 * @since 2019/12/16 13:39
 */
public class LoginFragment extends BaseMvpFragment<MainContract.Presenter> implements MainContract.View {


    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button)
    Button button;

    protected static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (mPresenter == null) {
            mPresenter = presenter;
        }
    }

    @Override
    protected void initViewOrData(View rootView) {
//           new DialogFragment().show(getChildFragmentManager(),"sds");
        Logger.d("手机信息"+ DeviceUtils.getDeviceInfo(getActivity().getApplicationContext()));
    }

    @Override
    protected void onFragmentFirstVisible() {

    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void loginSuccess(String result) {
        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        String name = editText.getText().toString();
        String pass = editText2.getText().toString();
        mPresenter.login(name,pass);
    }
}
