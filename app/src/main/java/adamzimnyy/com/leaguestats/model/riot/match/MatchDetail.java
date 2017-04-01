/*
 * Copyright 2014 Taylor Caldwell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adamzimnyy.com.leaguestats.model.riot.match;

import io.realm.RealmList;
import io.realm.RealmObject;

import java.io.Serializable;
import java.util.List;


public class MatchDetail extends RealmObject implements Serializable {

    private static final long serialVersionUID = 2606895296338330266L;

    private int mapId;
    private long matchCreation;
    private long matchDuration;
    private long matchId;
    private String matchMode;
    private String matchType;
    private String matchVersion;
    private RealmList<ParticipantIdentity> participantIdentities;
    private RealmList<Participant> participants;
    private String platformId;
    private String queueType;
    private String region;
    private String season;
    private RealmList<Team> teams;
  //  private Timeline timeline;

    public int getMapId() {
        return mapId;
    }

    public long getMatchCreation() {
        return matchCreation;
    }

    public long getMatchDuration() {
        return matchDuration;
    }

    public long getMatchId() {
        return matchId;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public String getMatchType() {
        return matchType;
    }

    public String getMatchVersion() {
        return matchVersion;
    }

    public RealmList<ParticipantIdentity> getParticipantIdentities() {
        return participantIdentities;
    }

    public RealmList<Participant> getParticipants() {
        return participants;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getQueueType() {
        return queueType;
    }

    public String getRegion() {
        return region;
    }

    public String getSeason() {
        return season;
    }

    public RealmList<Team> getTeams() {
        return teams;
    }

   // public Timeline getTimeline() {        return timeline;    }

    @Override
    public String toString() {
        return String.valueOf(getMatchId());
    }
}