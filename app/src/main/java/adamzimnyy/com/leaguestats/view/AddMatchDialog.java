package adamzimnyy.com.leaguestats.view;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.api.RetrofitBuilder;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Match;
import adamzimnyy.com.leaguestats.model.riot.match.MatchDetail;
import adamzimnyy.com.leaguestats.model.riot.match.Participant;
import adamzimnyy.com.leaguestats.model.riot.match.ParticipantIdentity;
import adamzimnyy.com.leaguestats.model.riot.recent.RecentGames;
import adamzimnyy.com.leaguestats.task.GetMostRecentMatchTask;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import io.realm.Realm;
import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

/**
 * Created by adamz on 31.03.2017.
 */

public class AddMatchDialog extends BottomSheetDialogFragment {
    private static final String TAG = "AddMatchDialog";

    Match lastMatch;
    @BindView(R.id.victory)
    TextView victoryText;

    @BindView(R.id.kda)
    TextView kdaText;

    @BindView(R.id.mode)
    TextView gamemodeText;

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.champion_image)
    CircularImageView championImage;
    BottomSheetBehavior behavior;
    FragmentActivity activity;

    @BindView(R.id.score_toggle)
    MultiStateToggleButton scoreToggle;

    @BindView(R.id.plus_toggle)
    MultiStateToggleButton plusToggle;
    View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = View.inflate(getContext(), R.layout.add_match_sheet, null);
        ButterKnife.bind(this, view);
        dialog.setContentView(view);
        behavior = BottomSheetBehavior.from((View) view.getParent());

        scoreToggle.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                Log.d(TAG, "Position: " + position);
            }
        });
        GetMostRecentMatchTask task = new GetMostRecentMatchTask(this, getContext());
        task.execute();
        return dialog;
    }

    public AddMatchDialog setActivity(FragmentActivity activity) {
        this.activity = activity;
        return this;
    }

    public void show() {
        show(activity.getSupportFragmentManager(), TAG);
    }

    public void display(Match match) {
        String image = Realm.getDefaultInstance().where(Champion.class).equalTo("key", match.getChampionKey()).findFirst().getImage().getFull();
        ImageLoader.getInstance().displayImage(RetrofitBuilder.RIOT_IMAGE + "champion/" + image, championImage);
        lastMatch = match;
        progress.setVisibility(View.GONE);
        victoryText.setText(match.isWin() ? "Victory" : "Defeat");
        kdaText.setText(match.getKills() + "/" + match.getDeaths() + "/" + match.getAssists());
        gamemodeText.setText(match.getMode());
    }

    public void display(RecentGames recentGames) {

    }

    @OnClick(R.id.save)
    public void saveMatch() {
        //TODO zółty button Update jeśli jest już zapisana

        //TODO karuzela z Reecent games, przerobić Task na ściąganie całego


        if (scoreToggle.getValue() < 0 || plusToggle.getValue() < 0) {

             Snackbar.make(view, "Not a valid score!", Snackbar.LENGTH_LONG).show();
            return;
        }

    int score = scoreToggle.getValue() * 3 + plusToggle.getValue() - 1;

        lastMatch.setScore(score);
    Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction()

    {
        @Override
        public void execute (Realm realm){
        realm.copyToRealmOrUpdate(lastMatch);
    }
    });

    dismiss();
}
}

