package adamzimnyy.com.leaguestats.model.realm.graph;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by adamz on 01.04.2017.
 */

public class Graph extends RealmObject{
    @PrimaryKey
    int id;
    int championId;
    RealmList<Filter> filters;

}
