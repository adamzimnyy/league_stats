package adamzimnyy.com.leaguestats.fragment.card;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Match;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by adamz on 01.04.2017.
 */

public class GraphCard extends LinearLayout {

    String championKey;

    @BindView(R.id.chart)
    BarChart chart;
    public GraphCard(Context context) {
        super(context);
        View v = inflate(context, R.layout.graph_layout, this);
        ButterKnife.bind(this,v);
    }

    public void makeMasteryChart() {
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
        chart.setData(new BarData(dataSet));
        chart.getData().setValueFormatter(new DefaultValueFormatter(0));
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(13.5f);
        xAxis.setLabelCount(14);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ScoreXAxisValueFormatter());
        xAxis.setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(true);
        chart.setDoubleTapToZoomEnabled(false);
        chart.invalidate();
    }

    public void setChampionKey(String championKey) {
        this.championKey = championKey;
    }

    public class ScoreXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues = {"D", " D+", " C-", "C", " C+", " B-", "B", " B+", " A-", "A", " A+", " S-", "S", " S+"};

        public ScoreXAxisValueFormatter() {

        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues[(int) value];
        }
    }
}
