package adamzimnyy.com.leaguestats.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.io.Serializable;

public class ChampionMastery extends RealmObject implements Serializable {

	private static final long serialVersionUID = 1980259216579071478L;

    @PrimaryKey
	private int championId;
    private int championLevel;
	private int championPoints;
	private long championPointsSinceLastLevel;
	private long championPointsUntilNextLevel;
	private boolean chestGranted;
	private long lastPlayTime;
	private long playerId;
	private int tokensEarned;

	public int getChampionId() {
		return championId;
	}

	public int getChampionLevel() {
		return championLevel;
	}

	public int getChampionPoints() {
		return championPoints;
	}

	public long getChampionPointsSinceLastLevel() {
		return championPointsSinceLastLevel;
	}

	public long getChampionPointsUntilNextLevel() {
		return championPointsUntilNextLevel;
	}

	public long getLastPlayTime() {
		return lastPlayTime;
	}

	public long getPlayerId() {
		return playerId;
	}

	public int getTokensEarned() {
		return tokensEarned;
	}

	public boolean isChestGranted() {
		return chestGranted;
	}

    @Override
    public String toString() {
        return "ChampionMastery{" +
                "championId=" + championId +
                ", championLevel=" + championLevel +
                ", championPoints=" + championPoints +
                ", championPointsSinceLastLevel=" + championPointsSinceLastLevel +
                ", championPointsUntilNextLevel=" + championPointsUntilNextLevel +
                ", chestGranted=" + chestGranted +
                ", lastPlayTime=" + lastPlayTime +
                ", playerId=" + playerId +
                ", tokensEarned=" + tokensEarned +
                '}';
    }
}