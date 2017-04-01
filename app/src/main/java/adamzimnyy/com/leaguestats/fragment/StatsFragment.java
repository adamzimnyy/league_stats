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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
