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
    String fieldName;
    RealmList<Filter> filters;

    boolean gameField;

    public boolean isStatsField() {
        return statsField;
    }

    public void setStatsField(boolean statsField) {
        this.statsField = statsField;
    }

    boolean statsField;

    public boolean isGameField() {
        return gameField;
    }

    public void setGameField(boolean gameField) {
        this.gameField = gameField;
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public RealmList<Filter> getFilters() {
        return filters;
    }

    public void setFilters(RealmList<Filter> filters) {
        this.filters = filters;
    }
}
