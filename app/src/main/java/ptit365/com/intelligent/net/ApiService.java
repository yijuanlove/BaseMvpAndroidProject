package ptit365.com.intelligent.net;

import java.util.List;

import io.reactivex.Flowable;
import ptit365.com.intelligent.base.BaseBean;
import ptit365.com.intelligent.bean.RestMenuBean;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/restaurant/findRestaurantMenuCategoryList")
    Flowable<BaseBean<List<RestMenuBean>>> getRestaurantMenuCategory();
}
