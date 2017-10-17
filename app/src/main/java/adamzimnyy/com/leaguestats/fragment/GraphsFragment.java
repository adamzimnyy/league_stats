package adamzimnyy.com.leaguestats.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.fragment.card.GraphCard;
import adamzimnyy.com.leaguestats.util.SizeChangeListener;
import adamzimnyy.com.leaguestats.view.CustomPager;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        root.addView(masteryChart,0);
        return v;
    }


    public static Fragment newInstance(String key, CustomPager viewPager) {
        GraphsFragment graphsFragment = new GraphsFragment();
        graphsFragment.setChampionKey(key);
        graphsFragment.setListener(viewPager);
        return graphsFragment;
    }


}
