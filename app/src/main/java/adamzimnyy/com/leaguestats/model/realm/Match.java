package adamzimnyy.com.leaguestats.model.realm;

import adamzimnyy.com.leaguestats.model.realm.graph.Graph;
import adamzimnyy.com.leaguestats.model.riot.match.MatchDetail;
import adamzimnyy.com.leaguestats.model.riot.recent.Game;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by adamz on 29.03.2017.
 */

public class Match extends RealmObject {

    @PrimaryKey
    private long id;
    private Game game;
    boolean blueSide;
    boolean win;
    String mode;
    int kills,deaths,assists;
    int map;
    private int championId;
    private String championKey;
    private int score;

    public String getChampionKey() {
        return championKey;
    }

    public void setChampionKey(String championKey) {
        this.championKey = championKey;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public boolean isBlueSide() {
        return blueSide;
    }

    public void setBlueSide(boolean blueSide) {
        this.blueSide = blueSide;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public int getChampionId() {
        return championId;
    }

    public void init(Game game){
        Champion champion = Realm.getDefaultInstance().where(Champion.class).equalTo("id", game.getChampionId()).findFirst();
        setWin(game.getStats().isWin());
        setAssists(game.getStats().getAssists());
        setMode(game.getSubType());
        setKills(game.getStats().getChampionsKilled());
        setDeaths(game.getStats().getNumDeaths());
        setGame(game);
        setBlueSide(game.getTeamId() == 100);
        setMap(game.getMapId());
        setChampionId(game.getChampionId());
        setId(game.getGameId());
        setChampionKey(champion != null ? champion.getKey() : "");
    }

    public class Field{

        public static final String BLUE_SIDE = "blueSide";
        public static final String WIN = "win";
        public static final String MODE ="mode";
        public static final String KILLS = "kills";
        public static final String DEATHS = "deaths";
        public static final String ASSISTS = "assists";
        public static final String SCORE = "score";
    }

    public enum Test{

    }
}
