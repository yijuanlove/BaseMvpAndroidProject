package ptit365.com.intelligent.base;

import com.uber.autodispose.AutoDisposeConverter;

public interface BaseView {
    /**
     * 加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据加载失败
     * @param throwable
     */
    void onError(Throwable throwable);

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     * @param <T>
     * @return
     */
    <T>AutoDisposeConverter<T> bindAutoDispose();

}
