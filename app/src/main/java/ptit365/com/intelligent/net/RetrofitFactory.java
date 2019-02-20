package ptit365.com.intelligent.net;

import android.os.Build;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import ptit365.com.intelligent.IntelligentConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static ApiService apiService;
    private static Retrofit mRetrofit;
    private static final int DEFAULT_OUTTIME = 10;
    protected static final Object monitor = new Object();

    private RetrofitFactory() {

    }

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_OUTTIME, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_OUTTIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_OUTTIME, TimeUnit.SECONDS)
//                .addInterceptor(getHeaderInterceptor())
                .addInterceptor(getInterceptor());
//        OkHttpClient client = builder.build();
        OkHttpClient client = enableTls12OnPreLollipop(builder).build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(IntelligentConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static ApiService getInstance() {
        synchronized (monitor) {
            if (apiService == null) {
                apiService = mRetrofit.create(ApiService.class);
            }
            return apiService;
        }
    }

    /**
     * 设置拦截器
     *
     * @return
     */
    private static Interceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //显示日志
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    /**
     *
     *
     * 4.2https访问
     */
    private static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));
                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();
                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
            }
        }
        return client;
    }

    /**
     * 设置Header
     *
     * @return
     */
//    private  static Interceptor getHeaderInterceptor() {
//        return new Interceptor() {
//            @Override
//            public Response intercept(@NonNull Chain chain) throws IOException {
//                Request original = chain.request();
//                Request.Builder requestBuilder = original.newBuilder()
//                        //添加Token
//                        .header("token", "");
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        };
//    }


//    private Interceptor HeaderInterceptor(){
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request origin = chain.request();
//                Request.Builder builder = origin.newBuilder();
//                builder.header("Content-Type","application/json")
//                        .header("Accept","application/json")
//                        .method(origin.method(),origin.body());
//                Request request =builder.build();
//                Response response =chain.proceed(request);
//                if(!BuildConfig.isRelease){
//                    LogUtils.e("发送请求==",String.format("%s",request.url()));
//                    ResponseBody responseBody = response.peekBody(1024 * 1024);
//                    LogUtils.e("响应结果==",String.format("%s",responseBody.string()));
//                }
//                return response;
//            }
//        };
//        return interceptor;
//    }
}
