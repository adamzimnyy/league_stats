package adamzimnyy.com.leaguestats.api.endpoint;

import adamzimnyy.com.leaguestats.model.riot.Summoner;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Map;

/**
 * Created by adamz on 27.03.2017.
 */

public interface SummonerService {

    @GET("{region}/v1.4/summoner/by-name/{name}")
    Call<Map<String,Summoner>> byName(@Path("region")String region, @Path("name") String name, @Query("api_key") String apiKey);
}
