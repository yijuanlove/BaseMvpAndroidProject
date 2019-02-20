package ptit365.com.intelligent;


import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import ptit365.com.intelligent.base.BaseActivity;
import ptit365.com.intelligent.base.BaseBean;
import ptit365.com.intelligent.bean.RestMenuBean;
import ptit365.com.intelligent.mvp.contract.RestMenuContract;
import ptit365.com.intelligent.mvp.presenter.RestMenuPresenter;

public class MainActivity extends BaseActivity<RestMenuPresenter> implements RestMenuContract.View {
    protected String TAG = this.getClass().getSimpleName();
    @Override
    protected int findLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mPresenter = new RestMenuPresenter();
        mPresenter.attachView(this);

        mPresenter.getRestaurantMenuCategory();
    }

    @Override
    public void onSuccess(BaseBean<List<RestMenuBean>> bean) {
        Log.d(TAG,"333333333333333333333333");
    }

    @Override
    public void showLoading() {
        Log.d(TAG,"11111111111111111111");
    }

    @Override
    public void hideLoading() {
        Log.d(TAG,"2222222222222222222222");
    }

    @Override
    public void onError(Throwable throwable) {
        Log.d(TAG,"44444444444444444444444");
    }
}
