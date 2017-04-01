package adamzimnyy.com.leaguestats.fragment;


import adamzimnyy.com.leaguestats.activity.ChampionActivity;
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

    @BindView(R.id.mastery_chart)
    BarChart masteryChart;


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
        ButterKnife.bind(this, v);

        makeMasteryChart();
        return v;
    }

    private void makeMasteryChart() {
        Realm realm = Realm.getDefaultInstance();
        Champion champion = realm.where(Champion.class).equalTo("key", championKey).findFirst();
        List<Match> matches = realm.where(Match.class).equalTo("championId", champion.getId()).findAll();
        int[] scores = new int[14];
        for (Match m : matches) {
            scores[m.getScore()]++;
        }
        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            entries.add(new BarEntry(i, scores[i]));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Label");
        dataSet.setColor(R.color.colorAccent);
        masteryChart.setData(new BarData(dataSet));
        masteryChart.getData().setValueFormatter(new DefaultValueFormatter(0));
        XAxis xAxis = masteryChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(13.5f);
        xAxis.setLabelCount(14);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new GraphsFragment.XAxisValueFormatter());
        xAxis.setDrawGridLines(false);
        masteryChart.getAxisRight().setEnabled(false);
        masteryChart.getAxisLeft().setAxisMinimum(0);
        masteryChart.getAxisLeft().setEnabled(false);
        masteryChart.setDrawGridBackground(false);
        masteryChart.getLegend().setEnabled(false);
        masteryChart.getDescription().setEnabled(false);
        masteryChart.setPinchZoom(false);
        masteryChart.setScaleEnabled(true);
        masteryChart.setDoubleTapToZoomEnabled(false);
        masteryChart.invalidate();
    }

    public static Fragment newInstance(String key, CustomPager viewPager) {
        GraphsFragment graphsFragment = new GraphsFragment();
        graphsFragment.setChampionKey(key);
        graphsFragment.setListener(viewPager);
        return graphsFragment;
    }

    public class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues = {"D", " D+", " C-", "C", " C+", " B-", "B", " B+", " A-", "A", " A+", " S-", "S", " S+"};

        public XAxisValueFormatter() {

        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues[(int) value];
        }
    }
}
