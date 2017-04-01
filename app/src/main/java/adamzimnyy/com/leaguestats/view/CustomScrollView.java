package adamzimnyy.com.leaguestats.view;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by adamz on 27.03.2017.
 */

public class CustomScrollView extends com.github.florent37.parallax.ScrollView{
  //  private BottomSheetBehavior sheetBehavior;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private GestureDetector mGestureDetector;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || super.onTouchEvent(ev);
    }
*/
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }
/*
    public void setSheetBehavior(BottomSheetBehavior sheetBehavior) {
        this.sheetBehavior = sheetBehavior;
    }

    public BottomSheetBehavior getSheetBehavior() {
        return sheetBehavior;
    }
*/
    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return (Math.abs(distanceY) > Math.abs(distanceX));
        }
    }
}
