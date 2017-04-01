package adamzimnyy.com.leaguestats.view;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.model.realm.Match;
import adamzimnyy.com.leaguestats.model.riot.recent.Game;
import adamzimnyy.com.leaguestats.model.riot.recent.RecentGames;
import adamzimnyy.com.leaguestats.task.GetRecentMatchesTask;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CirclePageIndicator;
import com.synnapps.carouselview.ViewListener;
import io.realm.Realm;
import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamz on 31.03.2017.
 */

public class AddMatchDialog extends BottomSheetDialogFragment {
    private static final String TAG = "AddMatchDialog";


    @BindView(R.id.progress)
    ProgressBar progress;

    BottomSheetBehavior behavior;
    FragmentActivity activity;

    @BindView(R.id.score_toggle)
    MultiStateToggleButton scoreToggle;

    @BindView(R.id.plus_toggle)
    MultiStateToggleButton plusToggle;
    View root;

    @BindView(R.id.carouselView)
    CarouselView carouselView;

    List<View> views = new ArrayList<>();
    List<Match> matches = new ArrayList<>();
    @BindView(R.id.save)
    Button saveButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        root = View.inflate(getContext(), R.layout.add_match_sheet, null);
        ButterKnife.bind(this, root);
        dialog.setContentView(root);
        behavior = BottomSheetBehavior.from((View) root.getParent());

        scoreToggle.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
            }
        });
        GetRecentMatchesTask task = new GetRecentMatchesTask(this, getContext());
        task.execute();


        carouselView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("carousel", "onPageSelected " + position);
                updateView(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return dialog;
    }

    private void updateView(int position) {
        Match match = Realm.getDefaultInstance().where(Match.class).equalTo("id", matches.get(position).getId()).findFirst();
        if (match != null) {
            Log.d("carousel", "updateView true");
            saveButton.setText("Update");
            saveButton.setBackgroundResource(R.color.colorUpdateButtonYellow);
            scoreToggle.setValue(Math.round(match.getScore() / 3f));
            plusToggle.setValue((match.getScore() + 1) % 3);
        } else {
            Log.d("carousel", "updateView false");
            saveButton.setText("Save");
            scoreToggle.setValue(-1);
            plusToggle.setValue(-1);
            saveButton.setBackgroundResource(R.color.colorSaveButtonGreen);
        }
        saveButton.invalidate();
    }

    public AddMatchDialog setActivity(FragmentActivity activity) {
        this.activity = activity;
        return this;
    }

    public void show() {
        show(activity.getSupportFragmentManager(), TAG);
    }

    public void display(RecentGames recentGames) {
        for (Game g : recentGames.getGames()) {
            Log.d("carousel", g.getGameId() + "");
            Match m = new Match();
            m.init(g);
            matches.add(m);
            MatchCard card = new MatchCard(activity);
            card.init(m);
            views.add(card);
        }
        CirclePageIndicator indicator = (CirclePageIndicator) carouselView.findViewById(R.id.indicator);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) indicator.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        indicator.setLayoutParams(params);
        carouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                return views.get(position);
            }
        });
        carouselView.setPageCount(matches.size());
        updateView(0);
        carouselView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.save)
    public void saveMatch() {
        //TODO zółty button Update jeśli jest już zapisana


        if (scoreToggle.getValue() < 0 || plusToggle.getValue() < 0) {

            Snackbar.make(root, "Not a valid score!", Snackbar.LENGTH_LONG).show();
            return;
        }

        int score = scoreToggle.getValue() * 3 + plusToggle.getValue() - 1;
        final Match match = matches.get(carouselView.getCurrentItem());
        match.setScore(score);
        Log.d("save", "Saving match " + match.getId() + ": " + match.getKills() + "/" + match.getDeaths() + "/" + match.getAssists());
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(match);
            }
        });
        updateView(carouselView.getCurrentItem());
        // dismiss();
    }
}

