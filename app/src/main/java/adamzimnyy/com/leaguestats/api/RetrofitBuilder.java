package adamzimnyy.com.leaguestats.api;

import android.provider.ContactsContract;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.util.Date;

public class RetrofitBuilder {
    private static boolean useLocalhost = false;



    public static final String BASE_URL = "http://mafia-test-env-java.eu-central-1.elasticbeanstalk.com/";
    public static final String RIOT_IMAGE ="http://ddragon.leagueoflegends.com/cdn/7.6.1/img/";
    public static final String RIOT_STATIC_DATA ="https://global.api.riotgames.com/api/lol/static-data/eune/v1.2/";
    public static final String LOCALHOST = "http://192.168.0.108:8080";

    public static Retrofit build(String url) {
        GsonBuilder builder = new GsonBuilder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss Z").
        create();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Object getService(Class<?> clas, String url) {
        return build(useLocalhost ? LOCALHOST : url).create(clas);
    }

}
