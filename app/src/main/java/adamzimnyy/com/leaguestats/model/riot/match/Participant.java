
package adamzimnyy.com.leaguestats.model.riot.match;

import io.realm.RealmObject;

import java.io.Serializable;
import java.util.List;


public class Participant extends RealmObject implements Serializable {

	private static final long serialVersionUID = -507075680096851928L;

	private int championId;
	private String highestAchievedSeasonTier;
	private int participantId;
	private int spell1Id;
	private int spell2Id;
	private ParticipantStats stats;
	private int teamId;
	private ParticipantTimeline timeline;

	public int getChampionId() {
		return championId;
	}

	public String getHighestAchievedSeasonTier() {
		return highestAchievedSeasonTier;
	}

	public int getParticipantId() {
		return participantId;
	}

	public int getSpell1Id() {
		return spell1Id;
	}

	public int getSpell2Id() {
		return spell2Id;
	}

	public ParticipantStats getStats() {
		return stats;
	}

	public int getTeamId() {
		return teamId;
	}

	public ParticipantTimeline getTimeline() {
		return timeline;
	}

	@Override
	public String toString() {
		return String.valueOf(getParticipantId());
	}
}