package adamzimnyy.com.leaguestats.activity;

import adamzimnyy.com.leaguestats.fragment.GraphsFragment;
import adamzimnyy.com.leaguestats.fragment.MatchesFragment;
import adamzimnyy.com.leaguestats.fragment.StatsFragment;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.view.AddMatchDialog;
import adamzimnyy.com.leaguestats.view.CustomPager;
import adamzimnyy.com.leaguestats.view.CustomScrollView;
import android.os.Bundle;
import adamzimnyy.com.leaguestats.R;
import android.support.annotation.NonNull;
import android.support.design.widget.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.clans.fab.FloatingActionButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import io.realm.Realm;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

public class ChampionActivity extends SwipeBackActivity {
    Champion champion;
    String championKey;
    @BindView(R.id.art)
    ImageView art;
    @BindView(R.id.name)
    TextView nameText;

    @BindView(R.id.view_pager)
    CustomPager viewPager;
    SectionsPagerAdapter adapter;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.mastery)
    ImageView mastery;

    @BindView(R.id.scroll_view)
    CustomScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        ButterKnife.bind(this);

        championKey = (String) getIntent().getSerializableExtra("extra");
        champion = Realm.getDefaultInstance().where(Champion.class).equalTo("key", championKey).findFirst();
        nameText.setText(champion.getName());
        Realm.getDefaultInstance().where(Champion.class).contains("key", champion.getKey()).findFirst().getImage();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + champion.getKey() + "_0.jpg", art);
        setupViewPager();
    }

    public void setMastery(int level) {
        switch (level) {
            case 6:
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.mastery6, mastery);
                break;
            case 7:
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.mastery7, mastery);
                break;
        }
        mastery.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
    }

    private void setupViewPager() {
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent e) {
                // How far the user has to scroll before it locks the parent vertical scrolling.
                final int margin = 10;
                final int fragmentOffset = v.getScrollX() % v.getWidth();

                if (fragmentOffset > margin && fragmentOffset < v.getWidth() - margin) {
                    viewPager.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        viewPager.setAdapter(adapter);
        //    viewPager.setSheetBehavior(sheetBehavior);
        viewPager.setOffscreenPageLimit(2);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_stats:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_graphs:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_matches:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return true;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_SETTLING || state == ViewPager.SCROLL_STATE_IDLE) {
                    updateNavigationBarState(viewPager.getCurrentItem());

                }
            }
        });
    }

    private void updateNavigationBarState(int position) {
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            ((MenuItemImpl) item).setExclusiveCheckable(false);
            item.setChecked(i == position);
            ((MenuItemImpl) item).setExclusiveCheckable(true);
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            fragments.add(StatsFragment.newInstance(championKey, viewPager));
            fragments.add(GraphsFragment.newInstance(championKey, viewPager));
            fragments.add(MatchesFragment.newInstance(championKey, viewPager));

            viewPager.measureFragments(fragments);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }

        private int mCurrentPosition = -1;

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                CustomPager pager = (CustomPager) container;
                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.setView(fragment.getView());
                }
            }
        }


    }
}
