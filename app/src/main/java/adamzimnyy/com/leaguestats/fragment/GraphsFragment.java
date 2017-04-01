package adamzimnyy.com.leaguestats.fragment;


import adamzimnyy.com.leaguestats.activity.ChampionActivity;
import adamzimnyy.com.leaguestats.fragment.card.GraphCard;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Match;
import adamzimnyy.com.leaguestats.util.SizeChangeListener;
import adamzimnyy.com.leaguestats.view.CustomPager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adamzimnyy.com.leaguestats.R;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import io.realm.Realm;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment {

    String championKey;
    SizeChangeListener listener;
    @BindView(R.id.root)
LinearLayout root;
    public SizeChangeListener getListener() {
        return listener;
    }

    public void setListener(SizeChangeListener listener) {
        this.listener = listener;
    }

    public String getChampionKey() {
        return championKey;
    }

    public void setChampionKey(String championKey) {
        this.championKey = championKey;
    }

    GraphCard masteryChart;

    public GraphsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.measure(0, View.MeasureSpec.UNSPECIFIED);
        if (listener != null) listener.onSizeChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_graphs, container, false);
        masteryChart = new GraphCard(getContext());
        ButterKnife.bind(this, v);
        masteryChart.setChampionKey(championKey);
        masteryChart.makeMasteryChart();
        root.addView(masteryChart);
        return v;
    }


    public static Fragment newInstance(String key, CustomPager viewPager) {
        GraphsFragment graphsFragment = new GraphsFragment();
        graphsFragment.setChampionKey(key);
        graphsFragment.setListener(viewPager);
        return graphsFragment;
    }


}
