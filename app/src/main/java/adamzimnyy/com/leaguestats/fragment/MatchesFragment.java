package adamzimnyy.com.leaguestats.fragment;


import adamzimnyy.com.leaguestats.model.realm.Champion;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {
String key;
    SizeChangeListener listener;

    public SizeChangeListener getListener() {
        return listener;
    }

    public void setListener(SizeChangeListener listener) {
        this.listener = listener;
    }

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.measure(0, View.MeasureSpec.UNSPECIFIED);
        if(listener!= null)    listener.onSizeChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_matches, container, false);

        return v;
    }

    public static Fragment newInstance(String key, CustomPager viewPager) {
        MatchesFragment f = new MatchesFragment();
        f.setKey(key);
        f.setListener(viewPager);
        return f;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
