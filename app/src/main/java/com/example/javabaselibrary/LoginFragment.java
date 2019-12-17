package com.example.javabaselibrary;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.corelibrary.app.mvp.BaseMvpFragment;
import com.example.corelibrary.db.mmkv.MmkvHelper;
import com.example.corelibrary.net.BaseResponse;
import com.example.corelibrary.net.LoadingListener;
import com.example.corelibrary.net.ResponseCallBack;
import com.example.corelibrary.net.retrofit.RetrofitHelper;
import com.example.corelibrary.net.rxjava.NetObserver;
import com.example.corelibrary.utils.DeviceUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

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
        /**
         * request请求入口
         * */
       APIService request =
                RetrofitHelper.getInstance().create(APIService.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("phon", "17315539722");
       RequestBody requestBody = convertJson(map);
        Observable<Phone> observable = request.postPhone(requestBody);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<>(new ResponseCallBack<Phone>() {
                    @Override
                    public void onSuccess(Phone phone) {
                        Logger.d(phone.toString());
                    }

                    @Override
                    public void onFault(int errorCode, String errorMsg) {
                        Logger.d(errorCode+errorMsg);
                    }
                }, new LoadingListener() {
                    @Override
                    public void showDialog() {
                        Logger.d("开始请求网络了 ");
                    }

                    @Override
                    public void dismissDialog() {
                        Logger.d("请求网络成功了 ");
                    }
                }));
         MMKV mmkv= MmkvHelper.getInstance().getMmkv();
    }

    /**
     * @param map 请求参数
     */
    public  RequestBody convertJson(HashMap map)
    {
        Gson gson = new Gson();
        String strEntity = gson.toJson(map);
        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json;charset=UTF-8"),
                strEntity);
        return body;
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
