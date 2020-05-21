package vn.touchspace.example.data.network;

import com.touchspace.example.BuildConfig;
import vn.touchspace.example.data.network.model.request.SignInRequest;
import vn.touchspace.example.data.network.model.response.SignIn;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

/**
 * Created by GNUD on 02/12/2017.
 */

@Singleton
public class AppApiHelper implements ApiService {

    private static final String TAG = AppApiHelper.class.getSimpleName();
    private static ApiService apiService;

    @Inject
    public AppApiHelper() {
    }


    public static void init() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);  // <-- this is the important line!
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private static RequestBody toRequestBody(JSONObject object) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    @Override
    public Single<SignIn> signIn(@Body SignInRequest signInRequest) {
        return apiService.signIn(signInRequest);
    }
}