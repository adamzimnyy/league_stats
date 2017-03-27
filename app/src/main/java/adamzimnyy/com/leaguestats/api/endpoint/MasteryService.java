package adamzimnyy.com.leaguestats.api.endpoint;

import adamzimnyy.com.leaguestats.api.constant.Server;
import adamzimnyy.com.leaguestats.model.realm.ChampionMastery;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by adamz on 27.03.2017.
 */

public interface MasteryService {

    @GET("location/{location}/player/{playerId}/champion/{championId}")
    Call<ChampionMastery> getSingleChampionMastery(@Path("location") String location, @Path("playerId") int summonerId,
                                                   @Path("championId") int championId, @Query("api_key") String apiKey);
}
