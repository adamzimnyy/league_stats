package adamzimnyy.com.leaguestats.view;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.api.RetrofitBuilder;
import adamzimnyy.com.leaguestats.constant.SummonerSpell;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.model.realm.Match;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.ImageLoader;
import io.realm.Realm;

/**
 * Created by adamz on 01.04.2017.
 */

public class MatchCard extends CardView {

    @BindView(R.id.champion_image)
    ImageView championImage;
    @BindView(R.id.spell1)
    ImageView spell1;
    @BindView(R.id.spell2)
    ImageView spell2;

    Match match;
    @BindView(R.id.victory)
    TextView victoryText;

    @BindView(R.id.kda)
    TextView kdaText;

    @BindView(R.id.mode)
    TextView gamemodeText;

    public MatchCard(Context context) {
        super(context);
        View v = inflate(context, R.layout.match_card, this);
        ButterKnife.bind(this, v);
    }

    public void init(Match match) {
        this.match = match;
        String image = Realm.getDefaultInstance().where(Champion.class).equalTo("key", match.getChampionKey()).findFirst().getImage().getFull();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(RetrofitBuilder.RIOT_IMAGE + "champion/" + image, championImage);
        loader.displayImage(RetrofitBuilder.RIOT_IMAGE + "spell/" + SummonerSpell.findById(match.getGame().getSpell1()).getKey()+".png", spell1);
        loader.displayImage(RetrofitBuilder.RIOT_IMAGE + "spell/" + SummonerSpell.findById(match.getGame().getSpell2()).getKey()+".png", spell2);

        victoryText.setText(match.isWin() ? "Victory" : "Defeat");
        kdaText.setText(match.getKills() + "/" + match.getDeaths() + "/" + match.getAssists());
        gamemodeText.setText(match.getMode());
    }
}
