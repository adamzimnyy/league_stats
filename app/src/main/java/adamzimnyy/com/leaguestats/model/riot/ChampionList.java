package adamzimnyy.com.leaguestats.model.riot;

import adamzimnyy.com.leaguestats.model.realm.Champion;

import java.util.Map;

/**
 * Created by adamz on 26.03.2017.
 */

public class ChampionList {

    String version;
    Map<String, Champion> data;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Champion> getData() {
        return data;
    }

    public void setData(Map<String, Champion> data) {
        this.data = data;
    }
}
