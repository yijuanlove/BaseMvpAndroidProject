package ptit365.com.intelligent.base;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import ptit365.com.intelligent.statusbar.ImmersiveStatusBarCompat;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{
    protected String TAG = this.getClass().getSimpleName();
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(findLayoutId());
        initView();

//        ImmersiveStatusBarCompat.setStatusBarColor(getWindow(),false,getResources().getColor(),true);
    }

    protected abstract int findLayoutId();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        if(mPresenter !=null){
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

}
