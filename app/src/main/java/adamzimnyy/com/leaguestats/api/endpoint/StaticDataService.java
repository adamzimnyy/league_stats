package adamzimnyy.com.leaguestats.api.endpoint;

import adamzimnyy.com.leaguestats.model.riot.ChampionList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by adamz on 26.03.2017.
 */

public interface StaticDataService {

    @GET("champion")
    Call<ChampionList> getChampionList(@Query("champData") String data, @Query("api_key") String apiKey);

    @GET("versions")
    Call<List<String>> versions(@Query("api_key") String key);
}
