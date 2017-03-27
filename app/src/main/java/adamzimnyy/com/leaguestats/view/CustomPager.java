package adamzimnyy.com.leaguestats.view;

import adamzimnyy.com.leaguestats.util.SizeChangeListener;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

public class CustomPager extends ViewPager implements SizeChangeListener {
    List<Fragment> fragments;
    private View currentView;
    int maxHeight = 0;
    int position;

    public CustomPager(Context context) {
        super(context);
    }

    public CustomPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (currentView == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
     /*   for (Fragment f : fragments) {
            View v = f.getView();
            if (v != null) {
                int h = v.getMeasuredHeight();
                if (h > maxHeight)
                    maxHeight = h;
            } else {
                Log.e("measure", "view is null");
            }
        }*/
        // currentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        //   int h = currentView.getMeasuredHeight();
        //  if (h > maxHeight) maxHeight = h;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void measureFragments(List<Fragment> list) {
        fragments = list;
    }


    public void setView(View view) {
        currentView = view;
    }


    public int measureFragment(View view) {
        if (view == null)
            return 0;
        view.measure(0, 0);
        return view.getMeasuredHeight();
    }

    @Override
    public void onSizeChanged() {
        for (Fragment f : fragments) {
            View v = f.getView();
            if (v != null) {
                int h = v.getMeasuredHeight();
                if (h > maxHeight)
                    maxHeight = h;
            } else {
                Log.e("measure", "view is null");
            }
        }
    }
}