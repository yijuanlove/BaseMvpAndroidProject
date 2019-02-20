package ptit365.com.intelligent.mvp.model;

import java.util.List;

import io.reactivex.Flowable;
import ptit365.com.intelligent.base.BaseBean;
import ptit365.com.intelligent.bean.RestMenuBean;
import ptit365.com.intelligent.mvp.contract.RestMenuContract;
import ptit365.com.intelligent.net.RetrofitFactory;

public class RestMenuModel implements RestMenuContract.Model {
    @Override
    public Flowable<BaseBean<List<RestMenuBean>>> getRestaurantMenuCategory() {
        return RetrofitFactory.getInstance().getRestaurantMenuCategory();
    }
}
