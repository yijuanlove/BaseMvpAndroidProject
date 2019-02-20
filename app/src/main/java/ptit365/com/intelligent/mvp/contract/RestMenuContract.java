package ptit365.com.intelligent.mvp.contract;

import java.util.List;

import io.reactivex.Flowable;
import ptit365.com.intelligent.base.BaseBean;
import ptit365.com.intelligent.base.BaseView;
import ptit365.com.intelligent.bean.RestMenuBean;

public interface RestMenuContract {

    interface Model{
      Flowable<BaseBean<List<RestMenuBean>>> getRestaurantMenuCategory();
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void onSuccess(BaseBean<List<RestMenuBean>> bean);
    }

    interface Presenter{
      void getRestaurantMenuCategory();
    }
}
