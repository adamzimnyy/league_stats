package adamzimnyy.com.leaguestats.fragment;


import adamzimnyy.com.leaguestats.activity.ChampionActivity;
import adamzimnyy.com.leaguestats.api.RetrofitBuilder;
import adamzimnyy.com.leaguestats.api.constant.Server;
import adamzimnyy.com.leaguestats.api.endpoint.MasteryService;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.ChampionMastery;
import adamzimnyy.com.leaguestats.util.Preference;
import adamzimnyy.com.leaguestats.util.Riot;
import adamzimnyy.com.leaguestats.util.SizeChangeListener;
import adamzimnyy.com.leaguestats.view.CustomPager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adamzimnyy.com.leaguestats.R;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {
    String key;
    Champion champion;

    @BindView(R.id.container)
    FrameLayout container;


    @BindView(R.id.left_mastery)
    ImageView leftMastery;

    @BindView(R.id.right_mastery)
    ImageView rightMastery;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.mastery_loading)
    ProgressBar masteryLoading;

    @BindView(R.id.total_points)
    TextView totalPoints;

    @BindView(R.id.next_level)
    TextView nextLevel;
    @BindView(R.id.percentage)
    TextView percentage;
    @BindView(R.id.level)
    TextView level;

    @BindView(R.id.chest)
    LinearLayout chest;


    public StatsFragment() {
        // Required empty public constructor
    }

    SizeChangeListener listener;

    public SizeChangeListener getListener() {
        return listener;
    }

    public void setListener(SizeChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (listener != null) listener.onSizeChanged();


        champion = Realm.getDefaultInstance().where(Champion.class).equalTo("key", key).findFirst();
        Log.d("champion", champion.getId() + "");
        getMastery();
    }

    private void getMastery() {
        MasteryService service = (MasteryService) RetrofitBuilder.getService(MasteryService.class, RetrofitBuilder.RIOT_MASTERY);
        String region = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Preference.SERVER, "");
        int summonerId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(Preference.SUMMONERS_ID, -1);
        Call<ChampionMastery> call = service.getSingleChampionMastery(Server.findByRegion(region).getLocation(), summonerId, champion.getId(), Riot.API_KEY);
container.setVisibility(View.INVISIBLE);
        masteryLoading.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ChampionMastery>() {
            @Override
            public void onResponse(Call<ChampionMastery> call, Response<ChampionMastery> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) drawMasteryCard(response.body());
                    if (response.code() == 204) drawMasteryCard(null);

                }
            }

            @Override
            public void onFailure(Call<ChampionMastery> call, Throwable t) {

            }
        });

    }

    private void drawMasteryCard(ChampionMastery mastery) {
        if (mastery != null) {
            Log.d("mastery", mastery.toString());
            totalPoints.setText(mastery.getChampionPoints() + " pts");
            chest.setVisibility(mastery.isChestGranted() ? View.VISIBLE : View.GONE);
            switch (mastery.getChampionLevel()) {
                case 1:
                    leftMastery.setImageResource(R.drawable.mastery1);
                    rightMastery.setImageResource(R.drawable.mastery2);
                    break;
                case 2:
                    leftMastery.setImageResource(R.drawable.mastery2);
                    rightMastery.setImageResource(R.drawable.mastery3);
                    break;
                case 3:
                    leftMastery.setImageResource(R.drawable.mastery3);
                    rightMastery.setImageResource(R.drawable.mastery4);
                    break;
                case 4:
                    leftMastery.setImageResource(R.drawable.mastery4);
                    rightMastery.setImageResource(R.drawable.mastery5);
                    break;
                case 5:
                    leftMastery.setImageResource(R.drawable.mastery5);
                    rightMastery.setImageResource(R.drawable.mastery6);
                    break;
                case 6:
                    leftMastery.setImageResource(R.drawable.mastery6);
                    ((ChampionActivity) getActivity()).setMastery(mastery.getChampionLevel());
                    rightMastery.setImageResource(R.drawable.mastery7);
                    break;
                case 7:
                    leftMastery.setImageResource(R.drawable.mastery7);
                    ((ChampionActivity) getActivity()).setMastery(mastery.getChampionLevel());
                    rightMastery.setImageResource(R.drawable.mastery7);
                    break;
            }
            if (mastery.getChampionLevel() < 5) {
                nextLevel.setText("Points to next level: " + mastery.getChampionPointsUntilNextLevel());
                int percent = (int) (100 * mastery.getChampionPointsSinceLastLevel() / (mastery.getChampionPointsSinceLastLevel() + mastery.getChampionPointsUntilNextLevel()));
                progressBar.setProgress(percent);
                percentage.setText(percent + "%");

            } else if (mastery.getChampionLevel() == 5) {
                nextLevel.setText("Tokens to next level: " + (2 - mastery.getTokensEarned()));
                progressBar.setProgress(50 * mastery.getTokensEarned());
            } else if (mastery.getChampionLevel() == 6) {
                nextLevel.setText("Tokens to next level: " + (3 - mastery.getTokensEarned()));
                progressBar.setProgress(33 * mastery.getTokensEarned());
            } else {
                nextLevel.setVisibility(View.INVISIBLE);
                progressBar.setProgress(100);
                percentage.setText("MAX");

            }
            level.setText(mastery.getChampionLevel() + "");



        }
        container.setVisibility(View.VISIBLE);
        masteryLoading.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public static StatsFragment newInstance(String key, CustomPager viewPager) {
        StatsFragment f = new StatsFragment();
        f.setKey(key);
        f.setListener(viewPager);
        return f;

    }

    public void setKey(String key) {
        this.key = key;
    }
}
