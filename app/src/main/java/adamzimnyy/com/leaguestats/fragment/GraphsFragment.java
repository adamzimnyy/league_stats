package adamzimnyy.com.leaguestats.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adamzimnyy.com.leaguestats.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment {


    public GraphsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graphs, container, false);
    }

}
