package ptit365.com.intelligent.mvp.presenter;

import java.util.List;

import io.reactivex.functions.Consumer;
import ptit365.com.intelligent.base.BaseBean;
import ptit365.com.intelligent.base.BasePresenter;
import ptit365.com.intelligent.bean.RestMenuBean;
import ptit365.com.intelligent.mvp.contract.RestMenuContract;
import ptit365.com.intelligent.mvp.model.RestMenuModel;
import ptit365.com.intelligent.net.RxScheduler;

public class RestMenuPresenter extends BasePresenter<RestMenuContract.View> implements RestMenuContract.Presenter {

    private RestMenuContract.Model model;

    public RestMenuPresenter() {
        model = new RestMenuModel();
    }

    @Override
    public void getRestaurantMenuCategory() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.getRestaurantMenuCategory()
                .compose(RxScheduler.<BaseBean<List<RestMenuBean>>>Flo_io_main())
                .as(mView.<BaseBean<List<RestMenuBean>>>bindAutoDispose())
                .subscribe(new Consumer<BaseBean<List<RestMenuBean>>>() {
                    @Override
                    public void accept(BaseBean<List<RestMenuBean>> restMenuBeanBaseBean) throws Exception {
                         mView.hideLoading();
                         mView.onSuccess(restMenuBeanBaseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.onError(throwable);
                    }
                });
    }
}
