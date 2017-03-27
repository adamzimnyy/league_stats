package adamzimnyy.com.leaguestats.activity;

import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Score;
import android.os.Bundle;
import adamzimnyy.com.leaguestats.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.nostra13.universalimageloader.core.ImageLoader;
import io.realm.Realm;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

public class ChampionActivity extends SwipeBackActivity {
    String key;
    String name;
    @BindView(R.id.mastery_chart)
    BarChart masteryChart;
    @BindView(R.id.art)
    ImageView art;
    @BindView(R.id.name)
    TextView nameText;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        ButterKnife.bind(this);
        key = getIntent().getExtras().getString("key");
        name = getIntent().getExtras().getString("name");
        nameText.setText(name);
        Realm.getDefaultInstance().where(Champion.class).contains("key", key).findFirst().getImage();
        ImageLoader loader =ImageLoader.getInstance();
        loader.displayImage("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+key+"_0.jpg",art);
        makeMasteryChart();
    }

    private void makeMasteryChart() {
        Realm realm = Realm.getDefaultInstance();
        Champion champion = realm.where(Champion.class).contains("key", getIntent().getStringExtra("key")).findFirst();
        List<Integer> scores = champion.getScore().getAsList();
        List<BarEntry> entries = new ArrayList<>();
        int c = 0;
        //  for (Integer i : scores) {
        //      entries.add(new BarEntry(c++, i));
        //  }
        entries.add(new BarEntry(c++, 40));
        entries.add(new BarEntry(c++, 16));

        entries.add(new BarEntry(c++, 2));
        entries.add(new BarEntry(c++, 5));

        entries.add(new BarEntry(c++, 11));
        entries.add(new BarEntry(c++, 17));


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
        xAxis.setValueFormatter(new XAxisValueFormatter());
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

    public class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues = {"D", "D+", "C-", "C", "C+", "B-", "B", "B+", "A-", "A", "A+", "S-", "S", "S+"};

        public XAxisValueFormatter() {

        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues[(int) value];
        }
    }
}
