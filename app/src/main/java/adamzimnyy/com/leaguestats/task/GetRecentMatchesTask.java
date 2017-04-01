package adamzimnyy.com.leaguestats.task;

import adamzimnyy.com.leaguestats.api.RetrofitBuilder;
import adamzimnyy.com.leaguestats.api.endpoint.MatchService;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Match;
import adamzimnyy.com.leaguestats.model.riot.recent.Game;
import adamzimnyy.com.leaguestats.model.riot.recent.RecentGames;
import adamzimnyy.com.leaguestats.util.Preference;
import adamzimnyy.com.leaguestats.util.Riot;
import adamzimnyy.com.leaguestats.view.AddMatchDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by adamz on 29.03.2017.
 */

public class GetRecentMatchesTask extends AsyncTask<Void, Void, RecentGames> {

    private Context context;
    private AddMatchDialog dialog;


    public GetRecentMatchesTask(AddMatchDialog addMatchDialog, Context context) {
        this.dialog = addMatchDialog;
        this.context = context;
    }

    @Override
    protected RecentGames doInBackground(Void... params) {
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
        if (recentResponse != null && recentResponse.isSuccessful()) {
          return recentResponse.body();
        }
        return null;
    }

    @Override
    protected void onPostExecute(RecentGames games) {
        dialog.display(games);
    }
}
