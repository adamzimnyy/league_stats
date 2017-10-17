package adamzimnyy.com.leaguestats.fragment.card;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Match;
import adamzimnyy.com.leaguestats.model.realm.graph.Filter;
import adamzimnyy.com.leaguestats.model.realm.graph.Graph;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by adamz on 01.04.2017.
 */

public class GraphCard extends LinearLayout {
    Graph graph;
    String championKey;

    @BindView(R.id.chart)
    BarChart chart;

    public GraphCard(Context context) {
        super(context);
        View v = inflate(context, R.layout.graph_layout, this);
        ButterKnife.bind(this, v);
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


    public void getData(Graph graph) throws NoSuchFieldException, IllegalAccessException {

        //filter matches
        Realm realm = Realm.getDefaultInstance();
        Champion champion = realm.where(Champion.class).equalTo("key", championKey).findFirst();
        RealmResults<Match> matches = realm.where(Match.class).equalTo("championId", champion.getId()).findAll();
        for (Filter f : graph.getFilters()) {
            String prefix = f.isGameField() ? "game." : f.isStatsField() ? "game.stats." : "";
            switch (f.getFieldType()) {
                case Filter.FilterType.BOOLEAN: {
                    if (f.isGameField())
                        matches = matches.where().equalTo(prefix + f.getFieldName(), Boolean.parseBoolean(f.getValue())).findAll();

                    break;
                }
                case Filter.FilterType.INTEGER: {
                    matches = matches.where().equalTo(prefix + f.getFieldName(), Integer.parseInt(f.getValue())).findAll();

                    break;
                }
                case Filter.FilterType.STRING: {
                    matches = matches.where().equalTo(prefix + f.getFieldName(), f.getValue()).findAll();
                    break;
                }
            }/*switch fieldType*/
        }/*for filter*/
        Log.d("graph", "matches size = " + matches.size());
        //mapa wartości do częstotlowosci wystepowania
        Map<Integer, Integer> map = new HashMap<>();
        for (Match m : matches) {
            int value = 0;
            if (graph.isGameField()) {
                value = (int) runGetter(graph.getFieldName(), m.getGame());
            } else if (graph.isStatsField()) {
                value = (int) runGetter(graph.getFieldName(), m.getGame().getStats());
            } else {
                value = (int) runGetter(graph.getFieldName(), m);
            }
            map.put(value, map.get(value) != null ? map.get(value) + 1 : 1);
        }
        Log.d("graph", "\tmap = " + map.toString());
    }

    public  Object runGetter(String field, Object o) {
        // MZ: Find the correct method
        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        return method.invoke(o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {

                        {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
        return null;
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
