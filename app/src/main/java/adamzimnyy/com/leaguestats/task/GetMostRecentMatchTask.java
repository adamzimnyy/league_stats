package adamzimnyy.com.leaguestats.task;

import adamzimnyy.com.leaguestats.api.RetrofitBuilder;
import adamzimnyy.com.leaguestats.api.endpoint.MatchService;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Match;
import adamzimnyy.com.leaguestats.model.riot.match.MatchDetail;
import adamzimnyy.com.leaguestats.model.riot.match.Participant;
import adamzimnyy.com.leaguestats.model.riot.match.ParticipantIdentity;
import adamzimnyy.com.leaguestats.model.riot.recent.Game;
import adamzimnyy.com.leaguestats.model.riot.recent.RecentGames;
import adamzimnyy.com.leaguestats.util.Preference;
import adamzimnyy.com.leaguestats.util.Riot;
import adamzimnyy.com.leaguestats.view.AddMatchDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by adamz on 29.03.2017.
 */

public class GetMostRecentMatchTask extends AsyncTask<Void, Void, Match> {

    private Context context;


    public GetMostRecentMatchTask(Context context) {

        this.context = context;
    }

    @Override
    protected Match doInBackground(Void... params) {
        int summonersId = PreferenceManager.getDefaultSharedPreferences(context).getInt(Preference.SUMMONERS_ID, -1);
        String region = PreferenceManager.getDefaultSharedPreferences(context).getString(Preference.SERVER, "");
        if (summonersId == -1 || region.isEmpty()) return null;

        MatchService service = (MatchService) RetrofitBuilder.getService(MatchService.class, RetrofitBuilder.RIOT_BASE);
        Call<RecentGames> recentCall = service.getRecentMatches(region, summonersId, Riot.API_KEY);
        Response<RecentGames> recentResponse = null;
        try {
            recentResponse = recentCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game game = null;
        if (recentResponse != null && recentResponse.isSuccessful()) {
            game = recentResponse.body().getGames().iterator().next();
            Champion champion = Realm.getDefaultInstance().where(Champion.class).equalTo("id", game.getChampionId()).findFirst();
            Match match = new Match();
            match.init(game);
            return match;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Match match) {
    }
}
