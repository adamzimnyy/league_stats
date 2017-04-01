package adamzimnyy.com.leaguestats.api.endpoint;

import adamzimnyy.com.leaguestats.model.realm.ChampionMastery;
import adamzimnyy.com.leaguestats.model.riot.match.MatchDetail;
import adamzimnyy.com.leaguestats.model.riot.matchlist.MatchList;
import adamzimnyy.com.leaguestats.model.riot.recent.RecentGames;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by adamz on 29.03.2017.
 */

public interface MatchService {

    @GET("region}/v2.2/matchlist/by-summoner/{summonerId}")
    Call<MatchList> getMatchHistory(@Path("region") String region,
                                          @Path("summonerId") int summonerId,
                                          @Query("championIds") String championIds,
                                          @Query("rankedQueues") String rankedQueues,
                                          @Query("seasons") String seasons,
                                          @Query("beginTime") int beginTime,
                                          @Query("endTime") int endTime,
                                          @Query("beginIndex") int beginIndex,
                                          @Query("endIndex") int endIndex,
                                          @Query("api_key") String apiKey);

    @GET("{region}/v2.2/match/{matchId}")
    Call<MatchDetail> getSingleMatchDetail(@Path("region") String region,
                                           @Path("matchId") long matchId,
                                           @Query("api_key") String apiKey);

    @GET("{region}/v1.3/game/by-summoner/{summonerId}/recent")
    Call<RecentGames> getRecentMatches(@Path("region") String region,
                                       @Path("summonerId") int summonerId,
                                       @Query("api_key") String apiKey);


}
