package adamzimnyy.com.leaguestats.model.realm;

import adamzimnyy.com.leaguestats.model.riot.match.MatchDetail;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by adamz on 29.03.2017.
 */

public class Match extends RealmObject{

    public MatchDetail detail;

    /**
     * Rating for match ranging from D to S+
     */
    public int score;
}
